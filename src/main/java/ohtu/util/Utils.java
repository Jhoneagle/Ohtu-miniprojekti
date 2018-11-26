package ohtu.util;

public class Utils {

    public Utils() {
    }

    public String parseUrlForTag(String string) {
        if (string == null) {
            return "";
        }
        if (string.contains("youtube.com")) {
            return "video";
        } else if (string.contains("dm.acm.org") || string.contains("ieeeexplore.ieeee.org")) {
            return "tieteellinen julkaisu";
        }
        return "";
    }
}
