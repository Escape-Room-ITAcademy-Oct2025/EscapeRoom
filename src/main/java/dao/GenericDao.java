package dao;

import java.util.List;

public interface GenericDao<T> {
    void save(T t);

    List<T> getAll();

    T getById(int id);

    void remove(T t);
}
