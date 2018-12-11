package ohtu.data_access;

import java.sql.Date;
import java.util.List;

import ohtu.domain.Kommentti;
import ohtu.domain.Vinkki;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KommenttiDaoTest extends TempFile {
    private KommenttiDao kommenttiDao;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        VinkkiDao vinkkiDao = new VinkkiDao(database);
        kommenttiDao = new KommenttiDao(database);
        vinkkiDao.add(new Vinkki(1, "Title", "Writer", "Book", "", new Date(System.currentTimeMillis()), null));
        vinkkiDao.add(new Vinkki(2, "Otsikko", "Kirjoittaja", "Kirja", "", new Date(System.currentTimeMillis()), null));
    }

    @Test
    public void addingKommenttiWorks() {
        assertEquals(0, kommenttiDao.findAll().size());
        kommenttiDao.add(component.generateKommentti());
        assertEquals(1, kommenttiDao.findAll().size());
    }

    @Test
    public void findOneByIdWorks() {
        Kommentti k = component.generateKommentti();
        kommenttiDao.add(k);
        Kommentti findOne = kommenttiDao.findOne(1);
        assertTrue(component.areKommentitSame(k, findOne));
    }

    @Test
    public void findOneReturnsNull() {
        Kommentti findOne = kommenttiDao.findOne(77);
        assertNull(findOne);
    }

    @Test
    public void findAllWorks() {
        Kommentti k1 = component.generateKommentti();
        Kommentti k2 = component.generateKommentti();
        Kommentti k3 = component.generateKommentti();
        kommenttiDao.add(k1);
        kommenttiDao.add(k2);
        kommenttiDao.add(k3);
        List<Kommentti> allComments = kommenttiDao.findAll();
        assertEquals(3, allComments.size());
        assertTrue(component.areKommentitSame(k1, allComments.get(0)));
        assertTrue(component.areKommentitSame(k2, allComments.get(1)));
        assertTrue(component.areKommentitSame(k3, allComments.get(2)));
    }

    @Test
    public void unValidDatabaseCausesError1() {
        kommenttiDao = new KommenttiDao(unValidDatabase());
        List<Kommentti> findAll = kommenttiDao.findAll();
        
        assertNull(findAll);
    }
    
    @Test
    public void unValidDatabaseCausesError2() {
        kommenttiDao = new KommenttiDao(unValidDatabase());
        Kommentti findOne = kommenttiDao.findOne(0);
        
        assertNull(findOne);
    }
    
    @Test
    public void unValidDatabaseCausesError3() {
        kommenttiDao = new KommenttiDao(unValidDatabase());
        kommenttiDao.add(component.generateKommentti());
        Kommentti findOne = kommenttiDao.findOne(0);
        
        assertNull(findOne);
    }
}
