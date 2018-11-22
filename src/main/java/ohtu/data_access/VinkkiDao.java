package ohtu.data_access;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import ohtu.domain.Vinkki;

public class VinkkiDao implements Dao<Vinkki, Integer> {

    private final Database database;

    public VinkkiDao(Database database) {
        this.database = database;
    }

    @Override
    public Vinkki findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * from Vinkki WHERE id=?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        
        Integer id = rs.getInt("id");
        String otsikko = rs.getString("otsikko");
        String tekija = rs.getString("tekija");
        String kuvaus = rs.getString("kuvaus");
        String linkki = rs.getString("linkki");
        String tagit = rs.getString("tagit");

        Vinkki etsitty = new Vinkki(id, otsikko, tekija, kuvaus, linkki);
        etsitty.setTagit(tagit);
        
        rs.close();
        stmt.close();
        conn.close();

        return etsitty;
    }

    @Override
    public List<Vinkki> findAll() throws SQLException {
        try {
            List<Vinkki> vinkit = new ArrayList<>();
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vinkki");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("id");
                String otsikko = rs.getString("otsikko");
                String tekija = rs.getString("tekija");
                String kuvaus = rs.getString("kuvaus");
                String linkki = rs.getString("linkki");
                String tagit = rs.getString("tagit");
                
                Vinkki f = new Vinkki(id, otsikko, tekija, kuvaus, linkki);
                f.setTagit(tagit);
                vinkit.add(f);
            }

            rs.close();
            stmt.close();
            conn.close();

            return vinkit;
        } catch (Exception e) {
            System.out.println("Exception");
        }

        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void add(Vinkki vinkki) throws SQLException {
        String vinkkiOtsikko = vinkki.getOtsikko();
        String vinkkiTekija = vinkki.getTekija();
        String vinkkiKuvaus = vinkki.getKuvaus();
        String vinkkiLinkki = vinkki.getLinkki();
        String tagit = vinkki.getTagit();


        /*if (findByName(vinkkiOtsikko) != null) {
            return;
        }*/
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Vinkki (otsikko, tekija, kuvaus, linkki, tagit) VALUES (?,?,?,?,?)");
        stmt.setString(1, vinkkiOtsikko);
        stmt.setString(2, vinkkiTekija);
        stmt.setString(3, vinkkiKuvaus);
        stmt.setString(4, vinkkiLinkki);
        stmt.setString(5, tagit);

        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
}
