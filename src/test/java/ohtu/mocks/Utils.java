package ohtu.mocks;

import java.sql.Date;
import ohtu.domain.Kommentti;
import ohtu.domain.Vinkki;

public class Utils {
    public Utils() {
        
    }
    
    public Vinkki generateVinkki() {
        int id = -1;
        String otsikko = "" + randomFloat();
        String tekija = "" + randomFloat();
        String kuvaus = "" + randomFloat();
        String linkki = "" + randomFloat();

        return new Vinkki(id, otsikko, tekija, kuvaus, linkki, new Date(1), null);
    }

    public Double randomFloat() {
        return Math.random() * 10000;
    }

    public boolean areVinkitSame(Vinkki expected, Vinkki actual) {
        if (!expected.getOtsikko().contains(actual.getOtsikko())) {
            return false;
        }

        if (!expected.getTekija().contains(actual.getTekija())) {
            return false;
        }

        if (!expected.getKuvaus().contains(actual.getKuvaus())) {
            return false;
        }

        if (!expected.getLinkki().contains(actual.getLinkki())) {
            return false;
        }

        return true;
    }
    
    public Kommentti generateKommentti() {
        int id = -1;
        int vinkkiId = 1;
        String nikki = "" + randomFloat();
        String content = "" + randomFloat();
        return new Kommentti(id, vinkkiId, nikki, content, new Date(System.currentTimeMillis()));
    }

    public boolean areKommentitSame(Kommentti expected, Kommentti actual) {
        if (!expected.getCreated().toString().contains(actual.getCreated().toString())) {
            return false;
        }
        
        if (!expected.getContent().contains(actual.getContent())) {
            return false;
        }
        
        if (!expected.getNikki().contains(actual.getNikki())) {
            return false;
        }
        
        if (expected.getVinkkiId() != actual.getVinkkiId()) {
            return false;
        }
        
        return true;
    }
}
