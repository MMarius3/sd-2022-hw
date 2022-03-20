package service.client;

import java.util.List;

public interface ClientService<T, ID extends Number> {

    List<T> findall();

    T findById(ID id);

    boolean save(T entity);

    boolean update(ID id, T newEntity);

    void removeAll();
}
