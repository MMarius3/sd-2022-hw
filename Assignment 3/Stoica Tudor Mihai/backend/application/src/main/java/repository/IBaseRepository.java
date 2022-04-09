package repository;

import java.util.List;
import java.util.Optional;

public interface IBaseRepository<T> {

    void insert(T object);

    void delete(T object);

    void deleteById(int id);

    void update(T object);

    Optional<T> getById(int id);

    List<Optional<T>> getAll();
}
