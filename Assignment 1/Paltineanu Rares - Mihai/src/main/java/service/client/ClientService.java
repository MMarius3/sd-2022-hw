package service.client;

import java.util.List;

public interface ClientService<T, ID extends Number> {

    List<T> findall();
    boolean save(T entity);

    void update(T oldEntity, T newEntity);

    T view(ID id);
}
