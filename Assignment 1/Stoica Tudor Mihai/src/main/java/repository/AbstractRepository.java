package repository;

import java.util.List;

public interface AbstractRepository<T> {

    void deleteByID(long id);
    void deleteAll();
    long create(T object);
    T getByID(long id);
    void update(T object);
    List<T> getAll();
}
