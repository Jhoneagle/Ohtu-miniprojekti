package ohtu.data_access;

import java.util.ArrayList;
import ohtu.domain.Vinkki;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class VinkkiDaoTest extends TempFile {
    private VinkkiDao dao;
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
        dao = new VinkkiDao(database);
    }
    
    @Test
    public void oneAddSucceeds() {
        dao.add(generateVinkki());
        
        assertEquals(1, dao.findAll().size());
    }
    
    @Test
    public void twoAddsSucceeds() {
        dao.add(generateVinkki());
        dao.add(generateVinkki());
        
        assertEquals(2, dao.findAll().size());
    }
        @Test
    public void deleteOneSucceeds() {
        dao.add(generateVinkki());
        dao.add(generateVinkki());
        dao.delete(1);
        
        assertEquals(1, dao.findAll().size());
    }
    
    @Test
    public void AddingRandomAmountSucceeds() {
        long times = Math.round(Math.random() * 20);
        addNTime(times);

        assertEquals(times, dao.findAll().size());
    }

    @Test
    public void addingLinkWillAddTag() {
        dao.add(new Vinkki(1, "Title", "Doer", "Doing titles", "youtube.com/"));
        assertEquals("video", dao.findOne(1).getTagit());
        Vinkki vinkki = new Vinkki(2, "Foo", "Bar", "", "youtube.com/roger");
        vinkki.setTagit("Tag,Magic");
        dao.add(vinkki);
        assertEquals("Tag,Magic,video", dao.findOne(2).getTagit());
    }

    
    @Test
    public void listOneVinkki() {
        Vinkki g = generateVinkki();
        dao.add(g);
        
        List<Vinkki> findAll = dao.findAll();
        assertEquals(1, findAll.size());
        assertTrue(areVinkitSame(g, findAll.get(0)));
    }
    
    @Test
    public void listThreeObjects() {
        Vinkki g1 = generateVinkki();
        dao.add(g1);
        
        Vinkki g2 = generateVinkki();
        dao.add(g2);
        
        Vinkki g3 = generateVinkki();
        dao.add(g3);
        
        List<Vinkki> findAll = dao.findAll();
        assertEquals(3, findAll.size());
        assertTrue(areVinkitSame(g1, findAll.get(0)));
        assertTrue(areVinkitSame(g2, findAll.get(1)));
        assertTrue(areVinkitSame(g3, findAll.get(2)));
    }
    
    @Test
    public void ListingNObjects() {
        long times = Math.round(Math.random() * 20);
        List<Vinkki> generatedOnes = new ArrayList<>();
        
        for (int index = 0; index < times; index++) {
            Vinkki g = generateVinkki();
            dao.add(g);
            generatedOnes.add(g);
        }
        
        List<Vinkki> findAll = dao.findAll();
        assertEquals(times, findAll.size());
        
        for (int index = 0; index < times; index++) {
            assertTrue(areVinkitSame(generatedOnes.get(index), findAll.get(index)));
        }
    }
    
    @Test
    public void findOnlyOne() {
        Vinkki g = generateVinkki();
        dao.add(g);
        
        assertTrue(areVinkitSame(g, dao.findOne(dao.findAll().get(0).getId())));
    }
    
    @Test
    public void findRightOne() {
        dao.add(generateVinkki());
        dao.add(generateVinkki());
        
        Vinkki g = generateVinkki();
        dao.add(g);
        
        dao.add(generateVinkki());
        
        assertTrue(areVinkitSame(g, dao.findOne(dao.findAll().get(2).getId())));
    }
    
    @Test
    public void findRightOne2() {
        dao.add(generateVinkki());
        
        Vinkki g = generateVinkki();
        dao.add(g);
        
        dao.add(generateVinkki());
        dao.add(generateVinkki());
        
        assertTrue(areVinkitSame(g, dao.findOne(dao.findAll().get(1).getId())));
    }
    
    @Test
    public void cantFindFromInvalidId() {
        long times = Math.round(Math.random() * 20);
        for (int index = 0; index < times; index++) {
            dao.add(generateVinkki());
        }
        
        assertNull(dao.findOne(7359));
    }
    
    @Test
    public void unValidDatabaseCausesError1() {
        dao = new VinkkiDao(new Database(null));
        List<Vinkki> findAll = dao.findAll();
        
        assertNull(findAll);
    }
    
    @Test
    public void unValidDatabaseCausesError2() {
        dao = new VinkkiDao(new Database(null));
        Vinkki findOne = dao.findOne(0);
        
        assertNull(findOne);
    }
    
    @Test
    public void unValidDatabaseCausesError3() {
        dao = new VinkkiDao(new Database(null));
        dao.add(generateVinkki());
        Vinkki findOne = dao.findOne(0);
        
        assertNull(findOne);
    }
    
    private Vinkki generateVinkki() {
        int id = -1;
        String otsikko = "" + randomFloat();
        String tekija = "" + randomFloat();
        String kuvaus = "" + randomFloat();
        String linkki = "" + randomFloat();
        
        return new Vinkki(id, otsikko, tekija, kuvaus, linkki);
    }
    
    private Double randomFloat() {
        return Math.random() * 10000;
    }
    
    private void addNTime(long times) {
        for (int index = 0; index < times; index++) {
            dao.add(generateVinkki());
        }
    }
    
    private boolean areVinkitSame(Vinkki expected, Vinkki actual) {
        if (!expected.getOtsikko().contains(actual.getOtsikko())) {
            return false;
        }
        
        if (!expected.getTekija().contains(actual.getTekija())) {
            return false;
        }
        
        if (!expected.getKuvaus().contains(actual.getKuvaus())) {
            return false;
        }
        
        if (!expected.getLinkki().contains(actual.getLinkki())) {
            return false;
        }
        
        return true;
    }
}
