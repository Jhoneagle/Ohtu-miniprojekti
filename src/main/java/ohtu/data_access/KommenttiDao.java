/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.util.List;
import ohtu.domain.Kommentti;

/**
 *
 * @author jxkokko
 */
public class KommenttiDao implements Dao<Kommentti, Integer> {
    private final Database database;

    public KommenttiDao(Database database) {
        this.database = database;
    }
    
    @Override
    public Kommentti findOne(Integer key) {
        return null;
    }

    @Override
    public List<Kommentti> findAll() {
        return null;
    }

    @Override
    public void delete(Integer key) {
        
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
