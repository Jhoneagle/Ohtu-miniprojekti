package ohtu.data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabasePostgres implements Database {
    public DatabasePostgres() {
        initializeSqlTables();
    }
    
    @Override
    public Connection getConnection() throws SQLException {
        String bdUrl = System.getenv("JDBC_DATABASE_URL");
        return DriverManager.getConnection(bdUrl);
    }
    
    @Override
    public final void initializeSqlTables() {
        try {
            getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS Vinkki(id SERIAL PRIMARY KEY NOT NULL, "
                    + "otsikko varchar(255), kuvaus varchar(10000), tekija varchar(100), linkki varchar(255)"
                    + ", tagit varchar(255), luettu date, isbn varchar(255))").execute();
            getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS Kommentti(id SERIAL PRIMARY KEY NOT NULL, "
                    + "vinkki_id INTEGER, nikki varchar(255), content varchar(10000), created date, "
                    + "FOREIGN KEY (vinkki_id) REFERENCES Vinkki(id) ON DELETE CASCADE)").execute();
        } catch (SQLException ex) {
            System.out.println("create tables failed!" + "\n" + ex);
        }
    }
}
