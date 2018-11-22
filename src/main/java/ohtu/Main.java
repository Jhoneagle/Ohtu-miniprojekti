package ohtu;

import java.sql.SQLException;
import java.util.*;
import ohtu.authentication.AuthenticationService;
import ohtu.data_access.AccountDao;
import ohtu.data_access.Dao;
import ohtu.data_access.Database;
import ohtu.data_access.UserDao;
import ohtu.data_access.VinkkiDao;
import ohtu.domain.Vinkki;
import ohtu.util.CreationStatus;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.velocity.VelocityTemplateEngine;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {
    static String LAYOUT = "templates/layout.html";
  
    static AccountDao userDao;
    static Dao vinkkiDao;
    static AuthenticationService authService;
    
    public static void main(String[] args) throws SQLException {
        port(findOutPort());
        Database database = new Database("jdbc:sqlite:vinkit.db");
        setAllDao(database);
        
        get("/", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/index.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());            
        
        get("/newVinkki", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/newVinkki.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());
        
        get("/vinkit", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("vinkit", vinkkiDao.findAll());
            return new ModelAndView(map, "vinkit");
        }, new ThymeleafTemplateEngine());
        
        post("/vinkit", (req,res) -> {
            String btnName = req.queryParams("action");  
            String otsikko = req.queryParams("otsikko");
            String tekija = req.queryParams("tekija");
            String kuvaus = req.queryParams("kuvaus");
            String linkki = req.queryParams("linkki");
            String tagit = req.queryParams("tagit");
            
            Vinkki vinkki = new Vinkki(-1, otsikko, tekija, kuvaus,linkki);
            vinkki.setTagit(tagit);
            vinkkiDao.add(vinkki);

            res.redirect("/vinkit");
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
        userDao = new UserDao(database);
        vinkkiDao = new VinkkiDao(database);
    }
    
    public static AuthenticationService authenticationService(){
        if (authService == null) {
           authService = new AuthenticationService(userDao); 
        }

        return authService;
    }    
      
    static int findOutPort() {
        if ( portFromEnv!=null ) {
            return Integer.parseInt(portFromEnv);
        }
        
        return 4567;
    }
    
    static String portFromEnv = new ProcessBuilder().environment().get("PORT");
    
    static void setEnvPort(String port){
        portFromEnv = port;
    }
}
