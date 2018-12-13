package ohtu.logistic;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ohtu.data_access.TempFile;
import ohtu.data_access.VinkkiDao;
import ohtu.domain.Vinkki;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class VinkkiLogicTest extends TempFile {
    private VinkkiLogic vinkkiController;
    private List<Vinkki> vinkit;
    private Vinkki vinkki1;
    private Vinkki vinkki2;
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
        this.vinkkiController = new VinkkiLogic(new VinkkiDao(database));

        vinkki1 = new Vinkki(1, "Title", "Doer", "Doing for lifebook", "youtube.com/", new Date(1), null);
        vinkki1.setTagit("lifestyle,book");
        vinkki2 = new Vinkki(2, "Wonderful life", "Amstrong", "Doer's do titles", "", null, null);
        vinkki2.setTagit("magic,book");
        vinkit = new ArrayList();
        vinkit.add(vinkki1);
        vinkit.add(vinkki2);
    }

    @Test
    public void addingLinkWillAddTag() {
        vinkkiController.add(new Vinkki(1, "Title", "Doer", "Doing titles", "youtube.com/", new Date(1), null));
        assertEquals("video", vinkkiController.findOne(1).getTagit());
        Vinkki vinkki = new Vinkki(2, "Foo", "Bar", "", "youtube.com/roger", new Date(1), null);
        vinkki.setTagit("Tag,Magic");
        vinkkiController.add(vinkki);
        assertEquals("Tag,Magic,video", vinkkiController.findOne(2).getTagit());
    }
    
    @Test
    public void ReadAfterSubmit() {
        vinkkiController.add(component.generateVinkki());
        vinkkiController.updateLuettuStatus(1, true);
        Vinkki findOne = vinkkiController.findOne(1);

        assertNotNull(findOne.getLuettu());
    }
    
    @Test
    public void unValidDatabaseCausesError5() {
        vinkkiController = new VinkkiLogic(new VinkkiDao(unValidDatabase()));
        vinkkiController.updateLuettuStatus(1, true);
        Vinkki findOne = vinkkiController.findOne(0);

        assertNull(findOne);
    }

    @Test
    public void filterVinkitByTagWorks() {
        assertEquals(0, vinkkiController.filterVinkitByTag(vinkit, "fox").size());
        assertEquals(1, vinkkiController.filterVinkitByTag(vinkit, "lifestyle").size());
        assertEquals(2, vinkkiController.filterVinkitByTag(vinkit, "BOOK").size());
        assertTrue(component.areVinkitSame(vinkki1, vinkkiController.filterVinkitByTag(vinkit, "lifestyle").get(0)));
    }

    @Test
    public void filterVinkitByVapaaSanaWorks() {
        assertEquals(2, vinkkiController.filterVinkitByVapaaSanahaku(vinkit, "title").size());
        assertEquals(1, vinkkiController.filterVinkitByVapaaSanahaku(vinkit, "strong").size());
        assertEquals(2, vinkkiController.filterVinkitByVapaaSanahaku(vinkit, "LIFe").size());
        assertEquals(2, vinkkiController.filterVinkitByVapaaSanahaku(vinkit, "doing,wonderful").size());
        assertEquals(0, vinkkiController.filterVinkitByVapaaSanahaku(vinkit, "canonot,find,anything").size());
        assertTrue(component.areVinkitSame(vinkki2, vinkkiController.filterVinkitByVapaaSanahaku(vinkit, "strong").get(0)));
    }

    @Test
    public void filterVinkitByNotReadWorks() {
        assertEquals(1, vinkkiController.filterVinkitByNotRead(vinkit).size());
        assertTrue(component.areVinkitSame(vinkki2, vinkkiController.filterVinkitByNotRead(vinkit).get(0)));
    }
    
    @Test
    public void removeLuettu() {
        
    }
}
