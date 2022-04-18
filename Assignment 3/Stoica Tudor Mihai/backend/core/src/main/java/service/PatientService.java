package service;

import dto.PatientDto;
import mapper.PatientMapper;
import org.springframework.stereotype.Service;
import repository.PatientRepository;

import java.util.Optional;

@Service
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
}
