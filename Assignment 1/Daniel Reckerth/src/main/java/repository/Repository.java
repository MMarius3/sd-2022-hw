package repository;

import java.util.List;
import java.util.Optional;

public interface Repository<ID, T>{

  List<T> findAll();

  Optional<T> findById(ID id);

  boolean save(T element);

  boolean update(ID id, T element);

  boolean deleteById(ID id);

  void deleteAll();
}
