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

    default T update(T updatedOne) {
        return null;
    }

    default void updateWithKey(K key) {
        System.out.println("not supported!");
    }

    default void removeDate(Integer vinkkiId) {
        System.out.println("not supported!");

    }
}
