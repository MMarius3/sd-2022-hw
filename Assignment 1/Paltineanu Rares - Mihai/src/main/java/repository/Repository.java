package repository;

import model.Client;
import model.User;

public interface Repository<I extends Number, T> {


    T findById(I id);

    boolean save(T user);

    boolean delete(I id);

    boolean update(I id, T newUser);
}
