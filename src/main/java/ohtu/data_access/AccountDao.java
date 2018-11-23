/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.util.List;

/**
 *
 * @author Johneagle
 */
public interface AccountDao<T,K> {
    T findOne(K key);

    List<T> findAll();

    void delete(K key);

    void add(T newOne);
    
    T findByName(String name);
}
