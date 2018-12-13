package ohtu;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import ohtu.data_access.*;
import ohtu.domain.Kommentti;
import ohtu.domain.Vinkki;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import ohtu.logistic.KommenttiLogic;
import ohtu.logistic.VinkkiLogic;

import ohtu.util.ISBNhandler;

import static spark.Spark.*;

public class Main {
    public static VinkkiLogic vinkkiController;
    public static KommenttiLogic kommenttiController;
    public static List<Vinkki> naytettavat;
    private static ISBNhandler handler;

    public static void main(String[] args) {
        port(findOutPort());

        Database database = getDatabase();
        setAllDao(database);
        naytettavat = new ArrayList<>();
        handler = new ISBNhandler();

        try {
            Class.forName("org.sqlite.JDBC");
            Class.forName("org.postgresql.Driver");

            Connection conn = database.getConnection();

            PreparedStatement statement = conn.prepareStatement("SELECT 1");
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                System.out.println("Yhteys onnistui");
            } else {
                System.out.println("Yhteys epäonnistui");
            }
        } catch (Exception ex) {
            System.out.println("epäonnistuu kirjastot!");
        }

        get("/", (request, response) -> {
            response.redirect("/vinkit");
            return "";
        });

        get("/base", (request, response) -> {
            HashMap map = new HashMap<>();
            map.put("template", "templates/index.html");
            map.put("naytettavat", naytettavat);
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/newVinkki", (request, response) -> {
            HashMap model = new HashMap<>();
            model.put("template", "templates/newVinkki.html");

            if (handler.isQueue()) {
                Vinkki data = handler.getData();

                model.put("vinkki", data);
                model.put("tagit", data.getTagit());
            } else {
                model.put("vinkki", new Vinkki(-1, "", "", "", "", new Date(1), null));
            }

            return new ModelAndView(model, "newVinkki");
        }, new ThymeleafTemplateEngine());

        get("/vinkit", (req, res) -> {
            String notRead = req.queryParams("notRead");
            ArrayList<Vinkki> vinkit = (ArrayList) vinkkiController.findAll();
            String searchType = req.queryParams("search");
            if ("Etsi tageilla".equals(searchType)) {
                vinkit = combineDisplayablesByTag(vinkit, req.queryParams("searchText"));
            } else if ("Vapaa sanahaku".equals(searchType)) {
                vinkit = combineDisplayablesByVapaaSanahaku(vinkit, req.queryParams("searchText"));
            }
            if ("notRead".equals(notRead)) {
                vinkit = (ArrayList) vinkit.stream()
                        .filter(vinkki -> null == vinkki.getLuettu())
                        .collect(Collectors.toList());
            }
            naytettavat = new ArrayList<>();
            HashMap map = new HashMap<>();
            map.put("vinkit", vinkit);
            return new ModelAndView(map, "vinkit");
        }, new ThymeleafTemplateEngine());

        get("/vinkki/:id", (req, res) -> {
            Integer vinkkiId = Integer.parseInt(req.params(":id"));
            Vinkki found = (Vinkki) vinkkiController.findOne(vinkkiId);
            
            HashMap map = new HashMap<>();
            map.put("vinkki", found);
            map.put("kommentit", kommenttiController.findAllByForeignKey(vinkkiId));
            
            if (found != null) {
                map.put("tagit", found.getTagit());

                if (found.getIsbn() != null && !found.getIsbn().isEmpty()) {
                    found.setIsbn(found.getIsbn().substring(0, found.getIsbn().length() - 1));
                }
            }

            return new ModelAndView(map, "vinkki");
        }, new ThymeleafTemplateEngine());

        get("/vinkinMuokkaus/:id", (req, res) -> {
            Integer vinkkiId = Integer.parseInt(req.params(":id"));
            Vinkki found = (Vinkki) vinkkiController.findOne(vinkkiId);
            HashMap map = new HashMap<>();
            map.put("vinkki", found);
            return new ModelAndView(map, "vinkinMuokkaus");
        }, new ThymeleafTemplateEngine());

        post("/vinkit", (req, res) -> {
            String btnName = req.queryParams("action");
            String otsikko = req.queryParams("otsikko");
            String tekija = req.queryParams("tekija");
            String kuvaus = req.queryParams("kuvaus");
            String linkki = req.queryParams("linkki");
            String tagit = req.queryParams("tagit");
            String isbn = req.queryParams("isbn");

            if (otsikko.isEmpty()) {
                res.redirect("/newVinkki");
                return "Vinkillä on oltava otsikko!";
            }

            Vinkki vinkki = new Vinkki(-1, otsikko, tekija, kuvaus, linkki, new Date(1), isbn);
            vinkki.setTagit(tagit);
            vinkkiController.add(vinkki);

            res.redirect("/vinkit");
            return "";
        });

        post("/isbn", (req, res) -> {
            String isbn = req.queryParams("isbn");
            handler.setIsbn(isbn);

            res.redirect("/newVinkki");
            return "";
        });

        post("/vinkit/:id", (req, res) -> {
            Integer vinkkiId = Integer.parseInt(req.queryParams("id"));
            vinkkiController.delete(vinkkiId);

            res.redirect("/vinkit");
            return "";
        });

        post("/luettu/:id", (req, res) -> {
            Integer vinkkiId = Integer.parseInt(req.queryParams("id"));
            vinkkiController.updateLuettuStatus(vinkkiId, true);

            res.redirect("/vinkki/" + vinkkiId);
            return "";
        });
        post("/lukematon/:id", (req, res) -> {
            Integer vinkkiId = Integer.parseInt(req.queryParams("id"));
            vinkkiController.updateLuettuStatus(vinkkiId, false);

            res.redirect("/vinkki/" + vinkkiId);
            return "";
        });

        post("/muokattu/:id", (req, res) -> {
            String s = req.queryParams("id").replace("/", "").trim();
            Integer id = Integer.parseInt(s);
            Vinkki vinkki = (Vinkki) vinkkiController.findOne(id);
            String otsikko = req.queryParams("otsikko");
            String tekija = req.queryParams("tekija");
            String kuvaus = req.queryParams("kuvaus");
            String linkki = req.queryParams("linkki");
            String tagit = req.queryParams("tagit");

            if (!otsikko.isEmpty()) {
                vinkki.setOtsikko(otsikko);
            }
            if (!tekija.isEmpty()) {
                vinkki.setTekija(tekija);
            }
            if (!kuvaus.isEmpty()) {
                vinkki.setKuvaus(kuvaus);
            }
            if (!linkki.isEmpty()) {
                vinkki.setLinkki(linkki);
            }
            if (!tagit.isEmpty()) {
                vinkki.setTagitAgain(tagit);
            }

            vinkkiController.update(vinkki);

            res.redirect("/vinkit");
            return "";
        });

        post("/kommentit/:vinkkiId", (req, res) -> {
            Integer vinkkiId = Integer.parseInt(req.queryParams("vinkkiId"));
            String nikki = req.queryParams("nikki");
            String content = req.queryParams("content");
            Kommentti kommentti = new Kommentti(-1, vinkkiId, nikki, content, null);
            kommenttiController.add(kommentti);
            res.redirect("/vinkki/" + vinkkiId);
            return "";
        });
    }

    private static ArrayList<Vinkki> combineDisplayablesByVapaaSanahaku(ArrayList<Vinkki> vinkit, String haku) {
        String[] etsittavat = haku.trim().toLowerCase().split(",");
        ArrayList<Vinkki> filteredVinkit = new ArrayList();
        for (String s : etsittavat) {
            String etsittava = s.trim();
            for (Vinkki vinkki : vinkit) {
                String otsikko = vinkki.getOtsikko().toLowerCase();
                String tekija = vinkki.getTekija().toLowerCase();
                String kuvaus = vinkki.getKuvaus().toLowerCase();
                System.out.println("etsittava: " + etsittava);
                System.out.println("otsikko: " + otsikko);
                if ((otsikko.contains(etsittava) || tekija.contains(etsittava) || kuvaus.contains(etsittava)) && naytettavat.indexOf(vinkki) == -1) {
                    naytettavat.add(vinkki);
                }
            }
        }
        
        return filteredVinkit;
    }

    private static ArrayList<Vinkki> combineDisplayablesByTag(ArrayList<Vinkki> vinkit, String haku) {

        String[] etsittavat = haku.trim().toLowerCase().split(",");
        ArrayList<Vinkki> filteredVinkit = new ArrayList();
        for (String s : etsittavat) {
            String etsittava = s.trim();
            for (Vinkki vinkki : vinkit) {
                String tagit = vinkki.getTagit();
                if (tagit.contains(etsittava) && filteredVinkit.indexOf(vinkki) == -1) {
                    filteredVinkit.add(vinkki);
                }
            }
        }
        
        return filteredVinkit;

    }

    public static void setAllDao(Database database) {
        if (vinkkiController == null) {
            vinkkiController = new VinkkiLogic(new VinkkiDao(database));
        }

        if (kommenttiController == null) {
            kommenttiController = new KommenttiLogic(new KommenttiDao(database));
        }
    }

    static int findOutPort() {
        if (portFromEnv != null) {
            return Integer.parseInt(portFromEnv);
        }

        return 4567;
    }

    static String portFromEnv = new ProcessBuilder().environment().get("PORT");

    static void setEnvPort(String port) {
        portFromEnv = port;
    }

    static Database getDatabase() {
        String bdUrl = System.getenv("JDBC_DATABASE_URL");
        
        if (bdUrl != null && bdUrl.length() > 0) {
            return new DatabasePostgres();
        } else {
            return new DatabaseSQLite("jdbc:sqlite:vinkit.db");
        }
    }
}
