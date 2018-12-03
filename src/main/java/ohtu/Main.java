package ohtu;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import ohtu.authentication.AuthenticationService;
import ohtu.data_access.*;
import ohtu.domain.Vinkki;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.*;

public class Main {

    static String LAYOUT = "templates/layout.html";

    public static AccountDao userDao;
    public static Dao vinkkiDao;
    public static Dao kommenttiDao;
    static AuthenticationService authService;
    public static List<Vinkki> naytettavat;

    public static void main(String[] args) {
        port(findOutPort());
        
        Database database = getDatabase();
        setAllDao(database);
        naytettavat = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            Class.forName("org.postgresql.Driver");
            
            Connection conn = database.getConnection();
            
            System.out.println(conn.toString());
            System.out.println(conn.getCatalog());
            System.out.println(conn.getSchema());
            System.out.println(conn);
            
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

        get("/newVinkki", (request, response) -> {
            HashMap model = new HashMap<>();
            model.put("template", "templates/newVinkki.html");
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
            HashMap map = new HashMap<>();
            map.put("vinkki", found);

            if (found != null) {
                map.put("tagit", found.getTagit());
            }

            return new ModelAndView(map, "vinkki");
        }, new ThymeleafTemplateEngine());

        get("/vinkinMuokkaus/:id", (req,res) -> {
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

            Vinkki vinkki = new Vinkki(-1, otsikko, tekija, kuvaus, linkki, new Date(1));
            vinkki.setTagit(tagit);
            vinkkiDao.add(vinkki);

            res.redirect("/vinkit");
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
                Integer id=Integer.parseInt(s);
                Vinkki vinkki = (Vinkki) vinkkiDao.findOne(id);
                String otsikko = req.queryParams("otsikko");
                String tekija = req.queryParams("tekija");
                String kuvaus = req.queryParams("kuvaus");
                String linkki = req.queryParams("linkki");
                String tagit = req.queryParams("tagit");
                if(!otsikko.isEmpty()) {
                    vinkki.setOtsikko(otsikko);
                } 
                if(!tekija.isEmpty()) {
                    vinkki.setTekija(tekija);
                } 
                if(!kuvaus.isEmpty()) {
                    vinkki.setKuvaus(kuvaus);
                } 
                if(!linkki.isEmpty()) {
                    vinkki.setLinkki(linkki);
                } 
                if(!tagit.isEmpty()) {
                    vinkki.setTagitAgain(tagit);
                } 
                
                vinkkiDao.update(vinkki);
         
            res.redirect("/vinkit");
            return "";
        });
        
        post("/", (req, res) -> {
            naytettavat = new ArrayList<>();
            String haku = req.queryParams("etsi");
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
            
            res.redirect("/");
            return "";
        });

        /*
        get("/login", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/login.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());     
        
        get("/user", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/user.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());            
        
        post("/login", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            String username = request.queryParams("username");
            String password = request.queryParams("password");
            
            if ( !authenticationService().logIn(username, password) ) {
                model.put("error", "invalid username or password");
                model.put("template", "templates/login.html");
                return new ModelAndView(model, LAYOUT);
            }
                
           response.redirect("/ohtu");
           return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());
        
        post("/user", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            String username = request.queryParams("username");
            String password = request.queryParams("password");
            String passwordConf = request.queryParams("passwordConfirmation");
            
            CreationStatus status = authenticationService().createUser(username, password, passwordConf);
            
            if ( !status.isOk()) {
                model.put("error", String.join(",  ", status.errors()));
                model.put("template", "templates/user.html");
                return new ModelAndView(model, LAYOUT);
            }
                
           response.redirect("/welcome");
           return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());
         */
    }

    public static void setAllDao(Database database) {
        if (userDao == null) {
            userDao = new UserDao(database);
        }
            
        if (vinkkiDao == null) {
            vinkkiDao = new VinkkiDao(database);
        }
            
        if (kommenttiDao == null) {
            kommenttiDao = new KommenttiDao(database);
        }
    }

    public static AuthenticationService authenticationService() {
        if (authService == null) {
            authService = new AuthenticationService(userDao);
        }

        return authService;
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
