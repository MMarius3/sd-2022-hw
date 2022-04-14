package repository.action;

import model.Action;

import java.sql.SQLException;
import java.util.List;

public interface ActionRepository {
    List<Action> findAll();
    List<Action> findAllWithId(Long id);
    boolean save(Action action) throws SQLException;
    void removeAll();
}
