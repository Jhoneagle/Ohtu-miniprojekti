package ohtu.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Vinkki {

    private Integer id;
    private String otsikko;
    private String tekija;
    private String kuvaus;
    private String linkki;
    private List<String> tagit;
    private Date luettu;
    private String isbn;

    public Vinkki(Integer id, String otsikko, String tekija, String kuvaus, String linkki, Date luettu, String isbn) {
        this.id = id;
        this.otsikko = otsikko;
        this.tekija = tekija;
        this.kuvaus = kuvaus;
        this.linkki = linkki;
        this.luettu = luettu;
        this.tagit = new ArrayList<>();
        this.isbn = isbn;
    }

    public int getId() {
        return this.id;
    }

    public String getOtsikko() {
        return this.otsikko;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    public String getTekija() {
        return this.tekija;
    }

    public void setTekija(String tekija) {
        this.tekija = tekija;
    }

    public String getLinkki() {
        return this.linkki;
    }

    public void setLinkki(String linkki) {
        this.linkki = linkki;
    }

    public String getKuvaus() {
        return this.kuvaus;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
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

    public void setTagitAgain(String tags) {
        this.tagit = new ArrayList<>();
        setTagit(tags);
    }

    public Date getLuettu() {
        return luettu;
    }

    public void setLuettu(Date date) {
        this.luettu = date;
    }

    public void setLuettuNull() {
        this.luettu = null;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
