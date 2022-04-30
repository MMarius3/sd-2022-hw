package repository;

import model.Action;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ActionRepository implements GenericRepository<Action>{
    @Override
    public int add(Action object) {
        return 0;
    }

    @Override
    public boolean delete(Action object) {
        return false;
    }

    @Override
    public boolean update(Action object) {
        return false;
    }

    @Override
    public Optional<Action> getByID(int id) {
        return null;
    }

    @Override
    public List<Optional<Action>> getAll() {
        return null;
    }

    public List<Action> getAllBetweenDates(LocalDateTime startDate, LocalDateTime endDate, int userID){
        return null;
    }
}
