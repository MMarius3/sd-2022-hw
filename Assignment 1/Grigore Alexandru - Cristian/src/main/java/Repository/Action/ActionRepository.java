package Repository.Action;

import Model.Action;
import Model.Client;

import java.util.List;
import java.util.Optional;

public interface ActionRepository {
    List<Action> findAll();

    Optional<Action> findById(Long id);

    boolean save(Action action);

    void removeAll();
}
