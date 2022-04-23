package com.core.service;

import com.core.dto.patient.PatientDto;
import com.core.mapper.PatientMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import com.infrastructure.repository.PatientRepository;

import java.util.Optional;

@Service
@ComponentScan({"mapper"})
@ComponentScan({"repository"})
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientService(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    public Optional<PatientDto> getPatientById(int id) {
        return patientRepository
                .findById(id)
                .map(patientMapper::patientToPatientDto);
    }

    public Optional<PatientDto> getPatientByName(String name) {
        return patientRepository
                .findByName(name)
                .map(patientMapper::patientToPatientDto);
    }

    public void createPatient(PatientDto patientDto) {
        patientRepository.save(patientMapper.patientDtoToPatient(patientDto));
    }

    public void updatePatient(PatientDto patientDto) {
        patientRepository.save(patientMapper.patientDtoToPatient(patientDto));
    }
}
