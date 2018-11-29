/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu;

import java.util.ArrayList;
import java.util.List;
import ohtu.data_access.Dao;
import ohtu.domain.Kommentti;

/**
 *
 * @author jxkokko
 */
public class KommenttiDaoForTests implements Dao<Kommentti, Integer> {
    private List<Kommentti> vinkit;
    private int nextId;

    public KommenttiDaoForTests() {
        this.vinkit = new ArrayList<>();
    }

    public List<Kommentti> getVinkit() {
        return vinkit;
    }

    public void setVinkit(List<Kommentti> vinkit) {
        this.vinkit = vinkit;
    }
    
    @Override
    public Kommentti findOne(Integer key) {
        return this.vinkit.get(key);
    }

    @Override
    public List<Kommentti> findAll() {
        return this.vinkit;
    }

    @Override
    public void delete(Integer key) {
        this.vinkit.remove((int) key);
        System.out.println("id problem be caution!!");
    }

    @Override
    public void add(Kommentti newOne) {
        
    }

    @Override
    public void updateWithKey(Integer key) {
        
    }

    @Override
    public Kommentti update(Kommentti updatedOne) {
        return null;
    }
}
