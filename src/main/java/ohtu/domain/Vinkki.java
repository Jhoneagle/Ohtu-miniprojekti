package ohtu.domain;

public class Vinkki {

    private Integer id;
    private String otsikko;
    private String tekija;
    private String kuvaus;

    private String linkki;

    public Vinkki(Integer id, String otsikko, String tekija, String kuvaus, String linkki) {
        this.id = id;
        this.otsikko = otsikko;
        this.tekija = tekija;
        this.kuvaus = kuvaus;
        this.linkki = linkki;
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

}
