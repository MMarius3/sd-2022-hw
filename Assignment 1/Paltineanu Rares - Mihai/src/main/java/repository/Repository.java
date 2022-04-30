package repository;

import java.util.List;

public interface Repository<T, I extends Number> {

    List<T> findAll();

    T findById(I id);

    boolean save(T entity);

    boolean delete(I id);

    boolean update(I id, T newEntity);
}
