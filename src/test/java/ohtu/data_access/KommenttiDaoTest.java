package ohtu.data_access;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ohtu.domain.Kommentti;
import ohtu.domain.Vinkki;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KommenttiDaoTest extends TempFile {
    private KommenttiDao kommenttiDao;
    private VinkkiDao vinkkiDao;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        vinkkiDao = new VinkkiDao(database);
        kommenttiDao = new KommenttiDao(database);
        vinkkiDao.add(new Vinkki(1, "Title", "Writer", "Book", "", new Date(System.currentTimeMillis())));
        vinkkiDao.add(new Vinkki(2, "Otsikko", "Kirjoittaja", "Kirja", "", new Date(System.currentTimeMillis())));
    }

    @Test
    public void addingKommenttiWorks() {
        assertEquals(0, kommenttiDao.findAll().size());
        kommenttiDao.add(generateKommentti());
        assertEquals(1, kommenttiDao.findAll().size());
    }

    @Test
    public void findOneByIdWorks() {
        Kommentti k = generateKommentti();
        kommenttiDao.add(k);
        Kommentti findOne = kommenttiDao.findOne(1);
        assertTrue(areKommentitSame(k, findOne));
    }

    @Test
    public void findOneReturnsNull() {
        Kommentti findOne = kommenttiDao.findOne(77);
        assertNull(findOne);
    }

    @Test
    public void findAllWorks() {
        Kommentti k1 = generateKommentti();
        Kommentti k2 = generateKommentti();
        Kommentti k3 = generateKommentti();
        kommenttiDao.add(k1);
        kommenttiDao.add(k2);
        kommenttiDao.add(k3);
        List<Kommentti> allComments = kommenttiDao.findAll();
        assertEquals(3, allComments.size());
        assertTrue(areKommentitSame(k1, allComments.get(0)));
        assertTrue(areKommentitSame(k2, allComments.get(1)));
        assertTrue(areKommentitSame(k3, allComments.get(2)));
    }

    @Test
    public void findAllByForeignKeyWorks() {
        long times = Math.round(Math.random() * 20);
        List<Kommentti> generatedOnes = new ArrayList<>();

        for (int index = 0; index < times; index++) {
            Kommentti k = generateKommentti();
            kommenttiDao.add(k);
            generatedOnes.add(k);
        }

        Kommentti k2 = new Kommentti(-1, 2, "FooBar", "barFoo", new Date(System.currentTimeMillis()));
        kommenttiDao.add(k2);

        List<Kommentti> allCommentsByForeignKey = kommenttiDao.findAllByForeignKey(2);
        assertEquals(1, allCommentsByForeignKey.size());
        assertTrue(areKommentitSame(k2, allCommentsByForeignKey.get(0)));

        allCommentsByForeignKey = kommenttiDao.findAllByForeignKey(1);
        for (int index = 0; index < times; index++) {
            assertTrue(areKommentitSame(generatedOnes.get(index), allCommentsByForeignKey.get(index)));
        }
        assertEquals(times + 1, kommenttiDao.findAll().size());
    }
    
    @Test
    public void unValidDatabaseCausesError1() {
        kommenttiDao = new KommenttiDao(new Database(null, true));
        List<Kommentti> findAll = kommenttiDao.findAll();
        
        assertNull(findAll);
    }
    
    @Test
    public void unValidDatabaseCausesError2() {
        kommenttiDao = new KommenttiDao(new Database(null, true));
        Kommentti findOne = kommenttiDao.findOne(0);
        
        assertNull(findOne);
    }
    
    @Test
    public void unValidDatabaseCausesError3() {
        kommenttiDao = new KommenttiDao(new Database(null, true));
        kommenttiDao.add(generateKommentti());
        Kommentti findOne = kommenttiDao.findOne(0);
        
        assertNull(findOne);
    }
    
    @Test
    public void unValidDatabaseCausesError4() {
        kommenttiDao = new KommenttiDao(new Database(null, true));
        List<Kommentti> found = kommenttiDao.findAllByForeignKey(0);
        
        assertNull(found);
    }

    private Kommentti generateKommentti() {
        int id = -1;
        int vinkkiId = 1;
        String nikki = "" + randomFloat();
        String content = "" + randomFloat();
        return new Kommentti(id, vinkkiId, nikki, content, new Date(System.currentTimeMillis()));
    }

    private Double randomFloat() {
        return Math.random() * 10000;
    }

    private boolean areKommentitSame(Kommentti expected, Kommentti actual) {
        if (expected.getCreated().toString().compareTo(actual.getCreated().toString()) == 0
                && expected.getContent().equals(actual.getContent())
                && expected.getNikki().equals(actual.getNikki())
                && expected.getVinkkiId() == actual.getVinkkiId()) {
            return true;
        }
        return false;
    }
}
