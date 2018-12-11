package ohtu.logistic;

import ohtu.data_access.Dao;
import ohtu.util.Utils;

public class VinkkiLogic {
    private final Utils utils;
    private final Dao dao;
    
    public VinkkiLogic(Dao dao) {
        utils = new Utils();
        this.dao = dao;
    }
    
    
}
