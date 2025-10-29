package dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {

    boolean save(T t);
    List<T> findAll();
    Optional<T> findById(int id);
    boolean remove(T t);
}
