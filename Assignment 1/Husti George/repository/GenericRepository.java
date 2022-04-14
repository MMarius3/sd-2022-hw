package repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<Type> {

    int add(Type object);

    boolean delete(Type object);

    boolean update(Type object);

    Optional<Type> getByID(int id);

    List<Optional<Type>> getAll();
}
