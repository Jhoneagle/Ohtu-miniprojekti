package ohtu.util;

import ohtu.domain.Kommentti;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UtilsTest {
    private Utils utils;

    @Before
    public void setUp() throws Exception {
        utils = new Utils();
    }

    @Test
    public void parseUrlForTag() {
        String urlTag = utils.parseUrlForTag("youtube.com/error");
        assertEquals("video", urlTag);
        urlTag = utils.parseUrlForTag("www.dm.acm.org/");
        assertEquals("tieteellinen julkaisu", urlTag);
        urlTag = utils.parseUrlForTag("ieeexplore.ieee.org/studiees");
        assertEquals("tieteellinen julkaisu", urlTag);
        urlTag = utils.parseUrlForTag("helsinki.fi/office");
        assertEquals("", urlTag);
        urlTag = utils.parseUrlForTag(null);
        assertEquals("", urlTag);
    }

    @Test
    public void sortCommentsByDateOrId() {
        Kommentti k1 = new Kommentti(1, 1, "Nick", "Name", new Date(1000));
        Kommentti k2 = new Kommentti(2, 1, "Wayne", "Batman", new Date(20000000));
        Kommentti k3 = new Kommentti(3, 1, "Hanno", "Koris", new Date(1000));
        List<Kommentti> kommentit = new ArrayList();
        kommentit.add(k1);
        kommentit.add(k2);
        kommentit.add(k3);
        kommentit = utils.sortCommentsByDateOrId(kommentit);
        assertEquals(k2, kommentit.get(0));
        assertEquals(k3, kommentit.get(1));
        assertEquals(k1, kommentit.get(2));
    }

    @Test
    public void sortCommentsByDateOrIdWhenZeroOrOneComment() {
        List<Kommentti> kommentit = new ArrayList();
        assertEquals(kommentit, utils.sortCommentsByDateOrId(kommentit));
        kommentit.add(new Kommentti(1, 1, "Nick", "Name", new Date(1000)));
        assertEquals(kommentit, utils.sortCommentsByDateOrId(kommentit));
    }
}