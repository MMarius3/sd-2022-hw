package com.infrastructure.repository;

import com.infrastructure.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Integer> {

    Optional<Consultation> findConsultationByPatient_Id(Integer id);

    @Override
    <S extends Consultation> S save(S entity);

    void deleteById(Integer id);
}
