package ohtu;

import ohtu.data_access.Dao;
import ohtu.domain.Vinkki;

import java.util.ArrayList;
import java.util.List;

public class VinkkiDaoForTests implements Dao<Vinkki, Integer> {
    private List<Vinkki> vinkit;

    public VinkkiDaoForTests() {
        this.vinkit = new ArrayList<>();
    }

    public List<Vinkki> getVinkit() {
        return vinkit;
    }

    public void setVinkit(List<Vinkki> vinkit) {
        this.vinkit = vinkit;
    }
    
    @Override
    public Vinkki findOne(Integer key) {
        return this.vinkit.get(key);
    }

    @Override
    public List<Vinkki> findAll() {
        return this.vinkit;
    }

    @Override
    public void delete(Integer key) {
        this.vinkit.remove((int) key);
    }

    @Override
    public void add(Vinkki newOne) {
        this.vinkit.add(newOne);
    }
    
}
