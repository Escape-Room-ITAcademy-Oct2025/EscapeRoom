package dao;

import java.util.List;

public interface GenericDao<T> {
    void save(T t);
    List<T> findAll();
    T findById(int id);
    void remove(T t);
}