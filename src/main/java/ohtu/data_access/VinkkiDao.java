package ohtu.data_access;

import ohtu.domain.Vinkki;
import ohtu.util.Utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VinkkiDao implements Dao<Vinkki, Integer> {
    private final Database database;
    private final Utils utils = new Utils();

    public VinkkiDao(Database database) {
        this.database = database;
    }

    @Override
    public Vinkki findOne(Integer key) {
        try {
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
            Date luettu = rs.getDate("luettu");

            Vinkki etsitty = new Vinkki(id, otsikko, tekija, kuvaus, linkki, luettu);
            etsitty.setTagit(tagit);

            rs.close();
            stmt.close();
            conn.close();

            return etsitty;
        } catch (SQLException ex) {
            System.out.println("ei toimi yhteys databaseen! \n" + ex);
            return null;
        }
    }

    @Override
    public List<Vinkki> findAll() {
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
                Date luettu = rs.getDate("luettu");

                Vinkki f = new Vinkki(id, otsikko, tekija, kuvaus, linkki, luettu);
                f.setTagit(tagit);
                vinkit.add(f);
            }

            rs.close();
            stmt.close();
            conn.close();

            return vinkit;
        } catch (SQLException ex) {
            System.out.println("ei toimi yhteys databaseen! \n" + ex);
            return null;
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Vinkki WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("ei toimi yhteys databaseen! \n" + ex);
        }
    }

    @Override
    public void add(Vinkki vinkki) {
        try {
            String vinkkiOtsikko = vinkki.getOtsikko();
            String vinkkiTekija = vinkki.getTekija();
            String vinkkiKuvaus = vinkki.getKuvaus();
            String vinkkiLinkki = vinkki.getLinkki();
            String tagit = vinkki.getTagit();

            String tag = utils.parseUrlForTag(vinkkiLinkki);
            if (!tag.isEmpty()) {
                if (tagit.isEmpty()) {
                    tagit = tag;
                } else {
                    tagit += "," + tag;
                }
            }

            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Vinkki (otsikko, tekija, kuvaus, linkki, tagit, luettu) VALUES (?,?,?,?,?,?)");
            stmt.setString(1, vinkkiOtsikko);
            stmt.setString(2, vinkkiTekija);
            stmt.setString(3, vinkkiKuvaus);
            stmt.setString(4, vinkkiLinkki);
            stmt.setString(5, tagit);
            stmt.setDate(6, null);
            
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("ei toimi yhteys databaseen! \n" + ex);
        }
    }
    
    @Override
    public void updateWithKey(Integer id) {
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE Vinkki SET luettu = ? WHERE id = ?");
            stmt.setDate(1, new Date(System.currentTimeMillis()));
            stmt.setInt(2, id);
            
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("ei toimi yhteys databaseen! \n" + ex);
        }
    }

    @Override
    public Vinkki update(Vinkki updatedOne) {
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE Vinkki SET otsikko=?, tekija=?, kuvaus=?, linkki=?, tagit=? WHERE id = ?");
            stmt.setString(1, updatedOne.getOtsikko());
            stmt.setString(2, updatedOne.getTekija());
            stmt.setString(3, updatedOne.getKuvaus());
            stmt.setString(4, updatedOne.getLinkki());
            stmt.setString(5, updatedOne.getTagit());
            stmt.setInt(6, updatedOne.getId());
            
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("ei toimi yhteys databaseen! \n" + ex);
        }
        
        return updatedOne;
    }

}
