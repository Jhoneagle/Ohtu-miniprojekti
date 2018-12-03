
package ohtu.data_access;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private final String databaseAddress;
    private final boolean test;
    private final String env;
    
    public Database(String databaseAddress, boolean test) {
        this.databaseAddress = databaseAddress;
        this.test = test;
        this.env = System.getenv("DATABASE_URL");
        
        initializeSqlTables();
    }
    
    public Connection getConnection() throws SQLException {
        if (this.env != null && !test) {
            try {
                URI dbUri = new URI(this.env);
                
                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
                
                return DriverManager.getConnection(dbUrl, username, password);
            } catch (URISyntaxException ex) {
                System.out.println("URL fail!");
            }
        }
        
        return DriverManager.getConnection(databaseAddress);
    }
    
    private void initializeSqlTables() {
        try {
            getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS Vinkki(id INTEGER PRIMARY KEY, "
                    + "otsikko varchar(255), kuvaus varchar(255), tekija varchar(100), linkki varchar(255)"
                    + ", tagit varchar(255), luettu date)").execute();
            getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS Kommentti(id INTEGER PRIMARY KEY, "
                    + "vinkki_id INTEGER, nikki varchar(255), content varchar(255), "
                    + "FOREIGN KEY (vinkki_id) REFERENCES Vinkki(id))").execute();
        } catch (SQLException ex) {
            return;
        }
    }
}
