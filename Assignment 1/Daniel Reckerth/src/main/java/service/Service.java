package service;

import java.util.List;

public interface Service<ID, T>{

  List<T> findAll();

  T findById(ID id);

  boolean save(T element);

  boolean update(ID id, T element);

  boolean deleteById(ID id);

  void deleteAll();
}
