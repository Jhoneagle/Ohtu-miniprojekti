/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.mocks;

import java.util.ArrayList;
import java.util.List;
import ohtu.data_access.Dao;
import ohtu.domain.Kommentti;

/**
 *
 * @author jxkokko
 */
public class KommenttiDaoForTests implements Dao<Kommentti, Integer> {
    private List<Kommentti> kommentit;
    private int nextId;

    public KommenttiDaoForTests() {
        this.kommentit = new ArrayList<>();
        this.nextId = 0;
    }

    public List<Kommentti> getKommentit() {
        return kommentit;
    }

    public void setKommentit(List<Kommentti> kommentit) {
        this.kommentit = kommentit;
    }
    
    @Override
    public Kommentti findOne(Integer key) {
        return this.kommentit.get(key);
    }

    @Override
    public List<Kommentti> findAll() {
        return this.kommentit;
    }

    @Override
    public void delete(Integer key) {
        this.kommentit.remove((int) key);
        System.out.println("id problem be caution!!");
    }

    @Override
    public void add(Kommentti newOne) {
        Kommentti kommentti = new Kommentti(nextId, newOne.getVinkkiId(), newOne.getNikki(), newOne.getContent(), newOne.getCreated());
        
        this.kommentit.add(kommentti);
        this.nextId++;
    }
}
