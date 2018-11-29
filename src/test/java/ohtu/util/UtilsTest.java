package ohtu.util;

import org.junit.Before;
import org.junit.Test;

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
}