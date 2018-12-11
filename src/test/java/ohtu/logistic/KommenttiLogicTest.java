package ohtu.logistic;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import ohtu.data_access.KommenttiDao;
import ohtu.data_access.TempFile;
import ohtu.data_access.VinkkiDao;
import ohtu.domain.Kommentti;
import ohtu.domain.Vinkki;
import ohtu.util.Utils;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class KommenttiLogicTest extends TempFile {
    private KommenttiLogic kommenttiController;
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
        VinkkiDao vinkkiDao = new VinkkiDao(database);
        this.kommenttiController = new KommenttiLogic(new KommenttiDao(database));
        vinkkiDao.add(new Vinkki(1, "Title", "Writer", "Book", "", new Date(System.currentTimeMillis()), null));
        vinkkiDao.add(new Vinkki(2, "Otsikko", "Kirjoittaja", "Kirja", "", new Date(System.currentTimeMillis()), null));
    }

    @Test
    public void findAllByForeignKeyWorks() {
        long times = Math.round(Math.random() * 20);
        List<Kommentti> generatedOnes = new ArrayList<>();

        for (int index = 0; index < times; index++) {
            Kommentti k = component.generateKommentti();
            kommenttiController.add(k);
            generatedOnes.add(k);
        }

        Kommentti k2 = new Kommentti(-1, 2, "FooBar", "barFoo", new Date(System.currentTimeMillis()));
        kommenttiController.add(k2);

        List<Kommentti> allCommentsByForeignKey = kommenttiController.findAllByForeignKey(2);
        assertEquals(1, allCommentsByForeignKey.size());
        assertTrue(component.areKommentitSame(k2, allCommentsByForeignKey.get(0)));

        allCommentsByForeignKey = kommenttiController.findAllByForeignKey(1);
        List<Kommentti> sortComments = new Utils().sortCommentsByDateOrId(generatedOnes);
        
        for (int index = 0; index < times; index++) {
            assertTrue(component.areKommentitSame(sortComments.get(index), allCommentsByForeignKey.get(index)));
        }
        
        assertEquals(times + 1, kommenttiController.findAll().size());
    }
    
    @Test
    public void unValidDatabaseCausesError4() {
        kommenttiController = new KommenttiLogic(new KommenttiDao(unValidDatabase()));
        List<Kommentti> found = kommenttiController.findAllByForeignKey(0);
        
        assertNull(found);
    }
}
