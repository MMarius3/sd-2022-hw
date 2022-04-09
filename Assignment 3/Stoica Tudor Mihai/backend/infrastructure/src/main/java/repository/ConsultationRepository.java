package repository;

import entity.Consultation;
import jpa_repository.JpaConsultationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Primary
@RequiredArgsConstructor
public class ConsultationRepository implements IConsultationRepository {

    private final JpaConsultationRepository jpaConsultationRepository;

    @Override
    public void insert(Consultation object) {
    }

    @Override
    public void delete(Consultation object) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void update(Consultation object) {

    }

    @Override
    public Optional<Consultation> getById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Optional<Consultation>> getAll() {
        return null;
    }
}
