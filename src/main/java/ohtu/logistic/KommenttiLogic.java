package ohtu.logistic;

import java.util.List;
import java.util.stream.Collectors;
import ohtu.data_access.Dao;
import ohtu.domain.Kommentti;
import ohtu.util.Utils;

public class KommenttiLogic {
    private final Utils utils;
    private final Dao dao;
    
    public KommenttiLogic(Dao dao) {
        utils = new Utils();
        this.dao = dao;
    }
    
    public Kommentti findOne(Integer key) {
        return (Kommentti) dao.findOne(key);
    }
    
    public List<Kommentti> findAll() {
        return (List<Kommentti>) dao.findAll();
    }
    
    public List<Kommentti> findAllByForeignKey(Integer key) {
        List<Kommentti> findAll = findAll();
        
        if (findAll == null) {
            return null;
        } else {
            List<Kommentti> collect = findAll.stream()
                    .filter(komentti -> komentti.getVinkkiId() == key)
                    .collect(Collectors.toList());
            
            return utils.sortCommentsByDateOrId(collect);
        }
    }
    
    public void delete(Integer key) {
        dao.delete(key);
    }
    
    public void add(Kommentti newOne) {
        dao.add(newOne);
    }
}
