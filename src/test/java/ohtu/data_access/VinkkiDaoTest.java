/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jxkokko
 */
public class VinkkiDaoTest extends TempFile {
    private VinkkiDao dao;
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
        dao = new VinkkiDao(database);
    }
    
    @Test
    public void testAdd() {
        
    }
    
    @Test
    public void listaa() {
        
    }
    
    @Test
    public void hae() {
        
    }
}
