package ohtu.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class VinkkiTest {

    @Test
    public void vinkkiConstructorSetsParameters() {
        Vinkki vinkki = new Vinkki(1, "Foo Bar", "Mark Doe", "hyva kirja", "testiurl");
        assertEquals(1, vinkki.getId());
        assertEquals("Foo Bar", vinkki.getOtsikko());
        assertEquals("Mark Doe", vinkki.getTekija());
        assertEquals("hyva kirja", vinkki.getKuvaus());
        assertEquals("testiurl", vinkki.getLinkki());

    }
}
