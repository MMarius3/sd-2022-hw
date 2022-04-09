package repository;
import java.util.List;
import java.util.Optional;
import entity.Patient;

public class PatientRepository implements IPatientRepository{

    @Override
    public void insert(Patient object) {

    }

    @Override
    public void delete(Patient object) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void update(Patient object) {

    }

    @Override
    public Optional<Patient> getById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Optional<Patient>> getAll() {
        return null;
    }
}
