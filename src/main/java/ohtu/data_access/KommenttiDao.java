package ohtu.data_access;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import ohtu.domain.Kommentti;

public class KommenttiDao implements Dao<Kommentti, Integer> {

    private final Database database;

    public KommenttiDao(Database database) {
        this.database = database;
    }
    
    @Override
    public Kommentti findOne(Integer key) {
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kommentti WHERE id=?");
            stmt.setObject(1, key);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Kommentti kommentti = createOneFrom(rs);
            rs.close();
            stmt.close();
            conn.close();

            return kommentti;
        } catch (SQLException ex) {
            System.out.println("ei toimi yhteys databaseen! \n" + ex);
            return null;
        }
    }

    @Override
    public List<Kommentti> findAll() {
        try {
            List<Kommentti> kommentit = new ArrayList<>();

            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kommentti");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Kommentti kommentti = createOneFrom(rs);
                kommentit.add(kommentti);
            }

            rs.close();
            stmt.close();
            conn.close();
            return kommentit;
        } catch (SQLException ex) {
            System.out.println("ei toimi yhteys databaseen! \n" + ex);
            return null;
        }
    }

    @Override
    public void delete(Integer key) {
        System.out.println("not supported!");
    }

    @Override
    public void add(Kommentti newOne) {
        try {
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kommentti (vinkki_id, nikki, content, created) VALUES (?,?,?,?)");
            stmt.setInt(1, newOne.getVinkkiId());
            stmt.setString(2, newOne.getNikki());
            stmt.setString(3, newOne.getContent());
            stmt.setDate(4, new Date(System.currentTimeMillis()));

            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("ei toimi yhteys databaseen! \n" + ex);
        }
    }
    
    private Kommentti createOneFrom(ResultSet rs) {
        try {
            Integer id = rs.getInt("id");
            Integer vinkkiId = rs.getInt("vinkki_id");
            String nikki = rs.getString("nikki");
            String content = rs.getString("content");
            Date created = rs.getDate("created");
            
            Kommentti kommentti = new Kommentti(id, vinkkiId, nikki, content, created);
            return kommentti;
        } catch (SQLException ex) {
            System.out.println("ei toimi! \n" + ex);
            return null;
        }
    }
}
