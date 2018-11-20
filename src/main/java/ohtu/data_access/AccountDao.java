/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Johneagle
 */
public interface AccountDao<T,K> {
    T findOne(K key) throws SQLException;

    List<T> findAll() throws SQLException;

    void delete(K key) throws SQLException;

    void add(T newOne) throws SQLException;
    
    T findByName(String name);
}
