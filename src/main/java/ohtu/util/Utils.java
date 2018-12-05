package ohtu.util;

import ohtu.domain.Kommentti;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Utils {
    public Utils() {
    }

    public String parseUrlForTag(String string) {
        if (string == null) {
            return "";
        }
        if (string.contains("youtube.com")) {
            return "video";
        } else if (string.contains("dm.acm.org") || string.contains("ieeexplore.ieee.org")) {
            return "tieteellinen julkaisu";
        }
        
        return "";
    }

    public List<Kommentti> sortCommentsByDateOrId(List<Kommentti> kom) {
        if (kom == null || kom.size() < 2) {
            return kom;
        }
        List<Kommentti> kommentit = kom;
        Collections.sort(kommentit, new Comparator<Kommentti>() {
            @Override
            public int compare(Kommentti kom1, Kommentti kom2) {
                if (kom1.getCreated().before(kom2.getCreated())) {
                    return 1;
                } else if (kom1.getCreated().after(kom2.getCreated())) {
                    return -1;
                }
                return Integer.compare(kom2.getId(), kom1.getId());
            }
        });
        
        return kommentit;
    }
}
