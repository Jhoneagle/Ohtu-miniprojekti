package ohtu.domain;

import java.util.ArrayList;
import java.util.List;

public class Vinkki {

    private Integer id;
    private String otsikko;
    private String tekija;
    private String kuvaus;
    private String linkki;
    private List<String> tagit;

    public Vinkki(Integer id, String otsikko, String tekija, String kuvaus, String linkki) {
        this.id = id;
        this.otsikko = otsikko;
        this.tekija = tekija;
        this.kuvaus = kuvaus;
        this.linkki = linkki;
        this.tagit = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public String getOtsikko() {
        return this.otsikko;
    }

    public String getTekija() {
        return this.tekija;
    }

    public String getLinkki() {
        return this.linkki;
    }

    public String getKuvaus() {
        return this.kuvaus;
    }

    public String getTagit() {
        String result = "";

        if (!this.tagit.isEmpty()) {
            result += this.tagit.get(0);
        }

        for (int i = 1; i < this.tagit.size(); i++) {
            String n = "," + this.tagit.get(i);
            result += n;
        }

        return result;
    }

    public List<String> getTagitAsList() {
        return this.tagit;
    }

    public void setTagit(String tags) {
        String[] parsed = tags.split(",");

        for (String t : parsed) {
            this.tagit.add(t);
        }
    }
}
