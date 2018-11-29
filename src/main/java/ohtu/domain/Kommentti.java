/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.domain;

/**
 *
 * @author jxkokko
 */
public class Kommentti {
    private int id;
    private int vinkkiId;
    private String nikki;
    private String content;

    public Kommentti(int id, int vinkkiId, String nikki, String content) {
        this.id = id;
        this.vinkkiId = vinkkiId;
        this.nikki = nikki;
        this.content = content;
    }
    
    public int getId() {
        return id;
    }

    public int getVinkkiId() {
        return vinkkiId;
    }

    public String getNikki() {
        return nikki;
    }

    public String getContent() {
        return content;
    }
}
