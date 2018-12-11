package ohtu.mocks;

import java.sql.Date;
import ohtu.data_access.Dao;
import ohtu.domain.Vinkki;

import java.util.ArrayList;
import java.util.List;
import ohtu.util.Utils;

public class VinkkiDaoForTests implements Dao<Vinkki, Integer> {

    private List<Vinkki> vinkit;
    private int nextId;

    public VinkkiDaoForTests() {
        this.vinkit = new ArrayList<>();
        this.nextId = 0;
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
        System.out.println("id problem be caution!!");
    }

    @Override
    public void add(Vinkki newOne) {
        String tag = new Utils().parseUrlForTag(newOne.getLinkki());

        if (!tag.isEmpty()) {
            if (newOne.getTagitAsList().isEmpty()) {
                newOne.setTagit(tag);
            } else {
                newOne.setTagit(newOne.getTagit() + "," + tag);
            }
        }

        Vinkki vinkki = new Vinkki(nextId, newOne.getOtsikko(), newOne.getTekija(), newOne.getKuvaus(), newOne.getLinkki(), new Date(1), null);
        vinkki.setTagit(newOne.getTagit());
        this.vinkit.add(vinkki);
        nextId++;
    }

    @Override
    public void updateWithKey(Integer key) {
        this.vinkit.get(key).getLuettu().setTime(System.currentTimeMillis());
    }

    @Override
    public Vinkki update(Vinkki updatedOne) {
        return this.vinkit.set(updatedOne.getId(), updatedOne);
    }

    @Override
    public void removeDate(Integer key) {
        this.vinkit.get(key).setLuettuNull();
    }
}
