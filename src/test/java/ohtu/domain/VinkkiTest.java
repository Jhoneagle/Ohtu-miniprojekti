package ohtu.domain;

import java.sql.Date;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VinkkiTest {
    private Vinkki vinkki;

    @Before
    public void setUp() {
        this.vinkki = new Vinkki(1, "Foo Bar", "Mark Doe", "hyva kirja", "testiurl", new Date(1), null);
    }

    @Test
    public void vinkkiConstructorSetsParameters() {
        assertEquals(1, vinkki.getId());
        assertEquals("Foo Bar", vinkki.getOtsikko());
        assertEquals("Mark Doe", vinkki.getTekija());
        assertEquals("hyva kirja", vinkki.getKuvaus());
        assertEquals("testiurl", vinkki.getLinkki());
    }

    @Test
    public void addingTagSucceeds() {
        this.vinkki.setTagit("moi");

        assertEquals(1, this.vinkki.getTagitAsList().size());
        assertEquals("moi", this.vinkki.getTagitAsList().get(0));
    }

    @Test
    public void adding2TagsSucceeds() {
        this.vinkki.setTagit("moi");
        this.vinkki.setTagit("moi uudestaan");

        assertEquals(2, this.vinkki.getTagitAsList().size());
        assertEquals("moi", this.vinkki.getTagitAsList().get(0));
        assertEquals("moi uudestaan", this.vinkki.getTagitAsList().get(1));
    }

    @Test
    public void addingMultipleTagsSameTimeSucceeds() {
        this.vinkki.setTagit("moi,uudestaan,kolmas");

        assertEquals(3, this.vinkki.getTagitAsList().size());
        assertEquals("moi", this.vinkki.getTagitAsList().get(0));
        assertEquals("uudestaan", this.vinkki.getTagitAsList().get(1));
        assertEquals("kolmas", this.vinkki.getTagitAsList().get(2));
    }

    @Test
    public void addingMultipleTagsSameTimeFails() {
        this.vinkki.setTagit("moi;uudestaan;kolmas, vasta toka");

        assertFalse(this.vinkki.getTagitAsList().size() == 3);
        assertEquals(2, this.vinkki.getTagitAsList().size());
        assertEquals("moi;uudestaan;kolmas", this.vinkki.getTagitAsList().get(0));
        assertEquals(" vasta toka", this.vinkki.getTagitAsList().get(1));
    }

    @Test
    public void addingTagsIsSeparatedByComma() {
        this.vinkki.setTagit("moi+moi,.-#%&/()=?!,kol<;:>mas");

        assertEquals(3, this.vinkki.getTagitAsList().size());
        assertEquals("moi+moi", this.vinkki.getTagitAsList().get(0));
        assertEquals(".-#%&/()=?!", this.vinkki.getTagitAsList().get(1));
        assertEquals("kol<;:>mas", this.vinkki.getTagitAsList().get(2));
    }

    @Test
    public void tagsReturnAsSameStringAsAdded() {
        String test = "yksi, kaksi, kolmas,neljäs,viides,kuudes";
        this.vinkki.setTagit(test);

        assertEquals(test, this.vinkki.getTagit());
    }

    @Test
    public void tagsReturnAsSameStringAsAdded2() {
        String test = "x.x-s,d+ds,gh&a";
        this.vinkki.setTagit(test);

        assertEquals(test, this.vinkki.getTagit());
    }

    @Test
    public void tagsReturnAsSameStringAsAdded3() {
        String test = "mololololololol";
        this.vinkki.setTagit(test);

        assertEquals(test, this.vinkki.getTagit());
    }

    @Test
    public void noTagsReturnEmptyString() {
        assertEquals("", this.vinkki.getTagit());
    }

    @Test
    public void noTagsReturnEmptyList() {
        assertEquals(0, this.vinkki.getTagitAsList().size());
    }

    @Test
    public void setOtsikkoTest() {
        this.vinkki.setOtsikko("otsikko");
        assertEquals("otsikko", this.vinkki.getOtsikko());
    }

    @Test
    public void setGetOtsikkoTest() {
        this.vinkki.setOtsikko("otsikko");
        assertEquals("otsikko", this.vinkki.getOtsikko());
    }

    @Test
    public void setGetTekijaTest() {
        this.vinkki.setTekija("tekija");
        assertEquals("tekija", this.vinkki.getTekija());
    }

    @Test
    public void setGetLinkkiTest() {
        this.vinkki.setLinkki("linkki");
        assertEquals("linkki", this.vinkki.getLinkki());
    }

    @Test
    public void setGetKuvausTest() {
        this.vinkki.setKuvaus("kuvaus");
        assertEquals("kuvaus", this.vinkki.getKuvaus());
    }
    
    @Test
    public void setTagitAgainTest() {
        this.vinkki.setTagitAgain("asd,qwe,zxc");
        assertEquals("asd,qwe,zxc", this.vinkki.getTagit());
    }
}
