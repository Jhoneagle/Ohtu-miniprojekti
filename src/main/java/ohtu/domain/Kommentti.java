package ohtu.domain;

import java.sql.Date;

public class Kommentti {
    private int id;
    private int vinkkiId;
    private String nikki;
    private String content;
    private Date created;

    public Kommentti(int id, int vinkkiId, String nikki, String content, Date created) {
        this.id = id;
        this.vinkkiId = vinkkiId;
        this.nikki = nikki;
        this.content = content;
        this.created = created;
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

    public Date getCreated() {
        return created;
    }
}
