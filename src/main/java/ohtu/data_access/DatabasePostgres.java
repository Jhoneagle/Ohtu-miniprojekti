/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Johneagle
 */
public class DatabasePostgres implements Database {
    public DatabasePostgres() {
        initializeSqlTables();
    }
    
    @Override
    public Connection getConnection() {
        try {
            String bdUrl = System.getenv("JDBC_DATABASE_URL");
            return DriverManager.getConnection(bdUrl);
        } catch (SQLException ex) {
            System.out.println("connection failed!" + "\n" + ex);
            return null;
        }
    }
    
    @Override
    public final void initializeSqlTables() {
        Connection connection = getConnection();
        
        if (connection == null) {
            return;
        }
        
        try {
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS Vinkki(id SERIAL PRIMARY KEY NOT NULL, "
                    + "otsikko varchar(255), kuvaus varchar(10000), tekija varchar(100), linkki varchar(255)"
                    + ", tagit varchar(255), luettu date, isbn varchar(255))").execute();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS Kommentti(id SERIAL PRIMARY KEY NOT NULL, "
                    + "vinkki_id INTEGER, nikki varchar(255), content varchar(10000), created date, "
                    + "FOREIGN KEY (vinkki_id) REFERENCES Vinkki(id))").execute();
        } catch (SQLException ex) {
            System.out.println("create tables failed!" + "\n" + ex);
        }
    }
}
