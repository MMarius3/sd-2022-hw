package jpa_repository;
import entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPatientRepository extends JpaRepository<Patient, Integer> {

}
