
package ohtu.data_access;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import ohtu.domain.Vinkki;

public class VinkkiDao implements Dao<Vinkki, Integer> {
    private Database database;
    
    public VinkkiDao(Database database) {
        this.database=database;
    }

    @Override
    public Vinkki findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt=conn.prepareStatement("SELECT * from Vinkki WHERE id=?");
        stmt.setObject(1, key);
        
        ResultSet rs= stmt.executeQuery();
        if(!rs.next()) {
            return null;
        }
        Integer id = rs.getInt("id");
        String otsikko = rs.getString("otsikko");
        String kirjoittaja = rs.getString("kirjoittaja");
        String tyyppi=rs.getString("tyyppi");
        
        Vinkki etsitty = new Vinkki(id, otsikko, kirjoittaja, tyyppi);
        rs.close();
        stmt.close();
        conn.close();
        
        return etsitty;
    }

    @Override
    public List<Vinkki> findAll() throws SQLException {
        
        try{
            List<Vinkki>vinkit = new ArrayList<>();
            Connection conn= database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vinkki");

            ResultSet rs = stmt.executeQuery();


            while(rs.next()) {
                Integer id = rs.getInt("id");
                String otsikko = rs.getString("otsikko");
                String kirjoittaja = rs.getString("kirjoittaja");
                String tyyppi=rs.getString("tyyppi");

                vinkit.add(new Vinkki(id, otsikko, kirjoittaja, tyyppi));
            }

            rs.close();
            stmt.close();
            conn.close();
            return vinkit;
        } catch(Exception e) {
            System.out.println("Exception");
        }        
        return null;
        
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
