package repositories;

public interface AbstractRepository<T> {
    void deleteByID();
    long create(T object);
    T getByID(long id);
    void update(T object);
}
