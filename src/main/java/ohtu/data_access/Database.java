
package ohtu.data_access;

import java.sql.*;

public class Database {
    private String databaseAddress;
    
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
                    + "otsikko varchar(255),kuvaus varchar(255), tekija varchar(100), linkki varchar(255), tagit varchar(255))").execute();
        } catch (SQLException ex) {
            return;
        }
    }
}
