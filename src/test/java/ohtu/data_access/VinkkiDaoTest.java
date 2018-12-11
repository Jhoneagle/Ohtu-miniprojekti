package ohtu.data_access;

import java.sql.Date;
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
        dao.add(component.generateVinkki());

        assertEquals(1, dao.findAll().size());
    }

    @Test
    public void twoAddsSucceeds() {
        dao.add(component.generateVinkki());
        dao.add(component.generateVinkki());

        assertEquals(2, dao.findAll().size());
    }

    @Test
    public void deleteOneSucceeds() {
        dao.add(component.generateVinkki());
        dao.add(component.generateVinkki());
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
    public void listOneVinkki() {
        Vinkki g = component.generateVinkki();
        dao.add(g);

        List<Vinkki> findAll = dao.findAll();
        assertEquals(1, findAll.size());
        assertTrue(component.areVinkitSame(g, findAll.get(0)));
    }

    @Test
    public void listThreeObjects() {
        Vinkki g1 = component.generateVinkki();
        dao.add(g1);

        Vinkki g2 = component.generateVinkki();
        dao.add(g2);

        Vinkki g3 = component.generateVinkki();
        dao.add(g3);

        List<Vinkki> findAll = dao.findAll();
        assertEquals(3, findAll.size());
        assertTrue(component.areVinkitSame(g1, findAll.get(0)));
        assertTrue(component.areVinkitSame(g2, findAll.get(1)));
        assertTrue(component.areVinkitSame(g3, findAll.get(2)));
    }

    @Test
    public void ListingNObjects() {
        long times = Math.round(Math.random() * 20);
        List<Vinkki> generatedOnes = new ArrayList<>();

        for (int index = 0; index < times; index++) {
            Vinkki g = component.generateVinkki();
            dao.add(g);
            generatedOnes.add(g);
        }

        List<Vinkki> findAll = dao.findAll();
        assertEquals(times, findAll.size());

        for (int index = 0; index < times; index++) {
            assertTrue(component.areVinkitSame(generatedOnes.get(index), findAll.get(index)));
        }
    }

    @Test
    public void findOnlyOne() {
        Vinkki g = component.generateVinkki();
        dao.add(g);

        assertTrue(component.areVinkitSame(g, dao.findOne(dao.findAll().get(0).getId())));
    }

    @Test
    public void findRightOne() {
        dao.add(component.generateVinkki());
        dao.add(component.generateVinkki());

        Vinkki g = component.generateVinkki();
        dao.add(g);

        dao.add(component.generateVinkki());

        assertTrue(component.areVinkitSame(g, dao.findOne(dao.findAll().get(2).getId())));
    }

    @Test
    public void findRightOne2() {
        dao.add(component.generateVinkki());

        Vinkki g = component.generateVinkki();
        dao.add(g);

        dao.add(component.generateVinkki());
        dao.add(component.generateVinkki());

        assertTrue(component.areVinkitSame(g, dao.findOne(dao.findAll().get(1).getId())));
    }

    @Test
    public void cantFindFromInvalidId() {
        long times = Math.round(Math.random() * 20);
        for (int index = 0; index < times; index++) {
            dao.add(component.generateVinkki());
        }

        assertNull(dao.findOne(7359));
    }

    @Test
    public void notReadInBegging() {
        dao.add(component.generateVinkki());
        Vinkki findOne = dao.findOne(1);

        assertNull(findOne.getLuettu());
    }

    @Test
    public void update() {
        Vinkki vinkki = new Vinkki(1, "Eka", "Eka", "", "youtube.com/Eka", new Date(1), null);
        Vinkki updated = new Vinkki(1, "Toka", "Toka", "", "youtube.com/toka", new Date(1), null);

        dao.add(vinkki);
        dao.update(updated);

        assertEquals("youtube.com/toka", dao.findOne(1).getLinkki());
    }

    @Test
    public void unValidDatabaseCausesError1() {
        dao = new VinkkiDao(unValidDatabase());
        List<Vinkki> findAll = dao.findAll();

        assertNull(findAll);
    }

    @Test
    public void unValidDatabaseCausesError2() {
        dao = new VinkkiDao(unValidDatabase());
        Vinkki findOne = dao.findOne(0);

        assertNull(findOne);
    }

    @Test
    public void unValidDatabaseCausesError3() {
        dao = new VinkkiDao(unValidDatabase());
        dao.add(component.generateVinkki());
        Vinkki findOne = dao.findOne(0);

        assertNull(findOne);
    }

    @Test
    public void unValidDatabaseCausesError4() {
        dao = new VinkkiDao(unValidDatabase());
        Vinkki update = dao.update(component.generateVinkki());

        assertNull(update);
    }

    @Test
    public void unValidDatabaseCausesError6() {
        dao = new VinkkiDao(unValidDatabase());
        dao.delete(1);
        Vinkki findOne = dao.findOne(0);

        assertNull(findOne);
    }
    
    private void addNTime(long times) {
        for (int index = 0; index < times; index++) {
            dao.add(component.generateVinkki());
        }
    }
}
