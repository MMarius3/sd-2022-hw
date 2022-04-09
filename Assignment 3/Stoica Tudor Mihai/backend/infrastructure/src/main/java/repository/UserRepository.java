package repository;

import entity.User;
import jpa_repository.JpaConsultationRepository;
import jpa_repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Primary
public class UserRepository implements IUserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public void insert(User object) {

    }

    @Override
    public void delete(User object) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void update(User object) {

    }

    @Override
    public Optional<User> getById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Optional<User>> getAll() {
        return null;
    }
}
