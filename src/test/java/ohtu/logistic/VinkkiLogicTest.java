package ohtu.logistic;

import java.sql.Date;
import ohtu.data_access.TempFile;
import ohtu.data_access.VinkkiDao;
import ohtu.domain.Vinkki;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class VinkkiLogicTest extends TempFile {
    private VinkkiLogic vinkkiController;
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
        this.vinkkiController = new VinkkiLogic(new VinkkiDao(database));
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
    public void removeLuettu() {
        
    }
}
