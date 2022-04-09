package jpa_repository;

import entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaConsultationRepository extends JpaRepository<Consultation, Integer> {

}
