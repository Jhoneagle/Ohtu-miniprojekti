
package ohtu.data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private final String databaseAddress;
    
    public Database(String databaseAddress) {
        this.databaseAddress = databaseAddress;
        initializeSqlTables();
    }
    
    public Connection getConnection() throws SQLException {
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
