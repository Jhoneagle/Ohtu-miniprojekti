package ohtu.data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseSQLite implements Database {
    private final String databaseAddress;
    
    public DatabaseSQLite(String databaseAddress) {
        this.databaseAddress = databaseAddress;
        
        initializeSqlTables();
    }
    
    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.databaseAddress);
    }
    
    @Override
    public final void initializeSqlTables() {
        try {
            getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS Vinkki(id INTEGER PRIMARY KEY, "
                    + "otsikko varchar(255), kuvaus varchar(10000), tekija varchar(100), linkki varchar(255)"
                    + ", tagit varchar(255), luettu date, isbn varchar(255))").execute();
            getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS Kommentti(id INTEGER PRIMARY KEY, "
                    + "vinkki_id INTEGER, nikki varchar(255), content varchar(10000), created date, "
                    + "FOREIGN KEY (vinkki_id) REFERENCES Vinkki(id) ON DELETE CASCADE)").execute();
        } catch (SQLException ex) {
            System.out.println("create tables failed!" + "\n" + ex);
        }
    }
}
