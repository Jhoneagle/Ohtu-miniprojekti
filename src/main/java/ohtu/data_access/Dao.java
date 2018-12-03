package ohtu.data_access;

import java.util.List;

public interface Dao<T, K> {
    T findOne(K key);

    List<T> findAll();

    default List<T> findAllByForeignKey(K key) {
        return null;
    }

    void delete(K key);

    void add(T newOne);
    
    void updateWithKey(K key);
    
    T update(T updatedOne);
}
