
package ohtu.domain;

public class Vinkki {
    private Integer id;
    private String otsikko;
    private String kirjoittaja;
    private String tyyppi;
    
    public Vinkki(Integer id, String otsikko, String kirjoittaja, String tyyppi) {
        this.id = id;
        this.otsikko = otsikko;
        this.kirjoittaja=kirjoittaja;
        this.tyyppi=tyyppi;
    }
    
    public String getOtsikko() {
        return otsikko;
    }
    
    public String getKirjoittaja() {
        return kirjoittaja;
    }
    
    public String getTyyppi() {
        return tyyppi;
    }
    
}
