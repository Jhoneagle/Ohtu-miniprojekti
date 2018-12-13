package ohtu.logistic;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ohtu.data_access.Dao;
import ohtu.domain.Vinkki;
import ohtu.util.Utils;

public class VinkkiLogic {
    private final Utils utils;
    private final Dao dao;

    public VinkkiLogic(Dao dao) {
        utils = new Utils();
        this.dao = dao;
    }

    public Vinkki findOne(Integer key) {
        return (Vinkki) dao.findOne(key);
    }

    public List<Vinkki> findAll() {
        return (List<Vinkki>) dao.findAll();
    }

    public void delete(Integer key) {
        dao.delete(key);
    }

    public void add(Vinkki newOne) {
        String vinkkiLinkki = newOne.getLinkki();
        String tag = utils.parseUrlForTag(vinkkiLinkki);

        if (!tag.isEmpty()) {
            newOne.setTagit(tag);
        }

        dao.add(newOne);
    }

    public Vinkki update(Vinkki updatedOne) {
        return (Vinkki) dao.update(updatedOne);
    }

    public void updateLuettuStatus(Integer id, boolean isIt) {
        Vinkki luettu = findOne(id);

        if (luettu == null) {
            return;
        }

        if (isIt) {
            luettu.setLuettu(new Date(System.currentTimeMillis()));
        } else {
            luettu.setLuettu(null);
        }

        dao.update(luettu);
    }

    public List<Vinkki> filterVinkitByVapaaSanahaku(List<Vinkki> vinkit, String haku) {
        String[] etsittavat = haku.trim().toLowerCase().split(",");
        List<Vinkki> filteredVinkit = new ArrayList();

        for (String s : etsittavat) {
            String etsittava = s.trim();
            for (Vinkki vinkki : vinkit) {
                String otsikko = vinkki.getOtsikko().toLowerCase();
                String tekija = vinkki.getTekija().toLowerCase();
                String kuvaus = vinkki.getKuvaus().toLowerCase();

                if ((otsikko.contains(etsittava) || tekija.contains(etsittava) || kuvaus.contains(etsittava)) && filteredVinkit.indexOf(vinkki) == -1) {
                    filteredVinkit.add(vinkki);
                }
            }
        }
        return filteredVinkit;
    }

    public List<Vinkki> filterVinkitByTag(List<Vinkki> vinkit, String haku) {
        String[] etsittavat = haku.trim().toLowerCase().split(",");
        List<Vinkki> filteredVinkit = new ArrayList();

        for (String s : etsittavat) {
            String etsittava = s.trim();
            for (Vinkki vinkki : vinkit) {
                String tagit = vinkki.getTagit().toLowerCase();
                if (tagit.contains(etsittava) && filteredVinkit.indexOf(vinkki) == -1) {
                    filteredVinkit.add(vinkki);
                }
            }
        }
        return filteredVinkit;
    }

    public List<Vinkki> filterVinkitByNotRead(List<Vinkki> vinkit) {
        return vinkit.stream()
                .filter(vinkki -> null == vinkki.getLuettu())
                .collect(Collectors.toList());
    }
}
