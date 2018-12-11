package ohtu.logistic;

import ohtu.data_access.Dao;
import ohtu.util.Utils;

public class KommenttiLogic {
    private final Utils utils;
    private final Dao dao;
    
    public KommenttiLogic(Dao dao) {
        utils = new Utils();
        this.dao = dao;
    }
    
    
}
