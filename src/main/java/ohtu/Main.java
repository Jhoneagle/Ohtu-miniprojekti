package ohtu;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import ohtu.data_access.*;
import ohtu.domain.Kommentti;
import ohtu.domain.Vinkki;
import ohtu.util.Utils;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;
import java.util.List;
import ohtu.util.ISBNhandler;

import static spark.Spark.*;

public class Main {
    public static Dao vinkkiDao;
    public static Dao kommenttiDao;
    public static List<Vinkki> naytettavat;
    private static Utils utils;
    private static ISBNhandler handler;

    public static void main(String[] args) {
        port(findOutPort());

        Database database = getDatabase();
        setAllDao(database);
        naytettavat = new ArrayList<>();
        utils = new Utils();
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
            HashMap map = new HashMap<>();
            map.put("template", "templates/index.html");
            map.put("naytettavat", naytettavat);
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

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
            naytettavat = new ArrayList<>();
            HashMap map = new HashMap<>();
            map.put("vinkit", vinkkiDao.findAll());
            return new ModelAndView(map, "vinkit");
        }, new ThymeleafTemplateEngine());

        get("/vinkki/:id", (req, res) -> {
            Integer vinkkiId = Integer.parseInt(req.params(":id"));
            Vinkki found = (Vinkki) vinkkiDao.findOne(vinkkiId);
            ArrayList<Kommentti> kommentit = (ArrayList) kommenttiDao.findAllByForeignKey(vinkkiId);
            
            HashMap map = new HashMap<>();
            map.put("vinkki", found);
            map.put("kommentit", utils.sortCommentsByDateOrId(kommentit));
            
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
            Vinkki found = (Vinkki) vinkkiDao.findOne(vinkkiId);
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
            vinkkiDao.add(vinkki);

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
            vinkkiDao.delete(vinkkiId);

            res.redirect("/vinkit");
            return "";
        });

        post("/luettu/:id", (req, res) -> {
            Integer vinkkiId = Integer.parseInt(req.queryParams("id"));
            vinkkiDao.updateWithKey(vinkkiId);

            res.redirect("/vinkit");
            return "";
        });

        post("/muokattu/:id", (req, res) -> {
            String s = req.queryParams("id").replace("/", "").trim();
            Integer id = Integer.parseInt(s);
            Vinkki vinkki = (Vinkki) vinkkiDao.findOne(id);
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

            vinkkiDao.update(vinkki);

            res.redirect("/vinkit");
            return "";
        });

        post("/kommentit/:vinkkiId", (req, res) -> {
            Integer vinkkiId = Integer.parseInt(req.queryParams("vinkkiId"));
            String nikki = req.queryParams("nikki");
            String content = req.queryParams("content");
            Kommentti kommentti = new Kommentti(-1, vinkkiId, nikki, content, null);
            kommenttiDao.add(kommentti);
            res.redirect("/vinkki/" + vinkkiId);
            return "";
        });

        post("/", (req, res) -> {
            combineDisplayables(req.queryParams("etsi"));

            res.redirect("/");
            return "";
        });

        post("/etsi", (req, res) -> {
            combineDisplayables(req.queryParams("etsi"));

            HashMap map = new HashMap<>();
            map.put("vinkit", naytettavat);

            return new ModelAndView(map, "vinkit");
        }, new ThymeleafTemplateEngine());
    }
    
    private static void combineDisplayables(String haku) {
        naytettavat = new ArrayList<>();
        String[] etsittavat = haku.trim().toLowerCase().split(",");
        List<Vinkki> vinkit = vinkkiDao.findAll();

        for (String s : etsittavat) {
            String etsittava = s.trim();
            for (Vinkki vinkki : vinkit) {
                String tagit = vinkki.getTagit();
                if (tagit.contains(etsittava) && naytettavat.indexOf(vinkki) == -1) {
                    naytettavat.add(vinkki);
                }
            }
        }

    }

    public static void setAllDao(Database database) {
        if (vinkkiDao == null) {
            vinkkiDao = new VinkkiDao(database);
        }

        if (kommenttiDao == null) {
            kommenttiDao = new KommenttiDao(database);
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
        return new Database("jdbc:sqlite:vinkit.db", false);
    }
}
