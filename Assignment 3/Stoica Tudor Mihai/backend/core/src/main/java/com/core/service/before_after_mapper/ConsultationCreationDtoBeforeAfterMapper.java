package com.core.service.before_after_mapper;

import com.core.dto.consultation.ConsultationCreationDto;
import com.infrastructure.entity.Consultation;
import com.infrastructure.repository.PatientRepository;
import com.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ConsultationCreationDtoBeforeAfterMapper implements IBeforeAfterMapper<ConsultationCreationDto, Consultation> {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public ConsultationCreationDtoBeforeAfterMapper(PatientRepository patientRepository, UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Consultation before(ConsultationCreationDto consultationCreationDto) {
        return null;
    }

    @Override
    public Consultation after(ConsultationCreationDto consultationCreationDto, Consultation partiallyMappedDestination) throws Exception {
        partiallyMappedDestination.setPatient(patientRepository
                        .findById(consultationCreationDto.getPatientId())
                        .orElseThrow(() -> new Exception("Patient not found")))
                .setDoctor(userRepository
                        .findById(consultationCreationDto.getDoctorId())
                        .orElseThrow(() -> new Exception("Patient not found")));

        return partiallyMappedDestination;
    }
}
