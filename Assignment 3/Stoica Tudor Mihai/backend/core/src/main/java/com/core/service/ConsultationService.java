package com.core.service;

import com.core.dto.consultation.ConsultationCreationDto;
import com.core.dto.consultation.ConsultationDto;
import com.core.dto.consultation.ConsultationUpdateDto;
import com.core.mapper.ConsultationMapper;
import com.core.service.before_after_mapper.IBeforeAfterMapper;
import com.infrastructure.entity.Consultation;
import com.infrastructure.repository.ConsultationRepository;
import com.infrastructure.repository.PatientRepository;
import com.infrastructure.repository.UserRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ComponentScan({"mapper"})
@ComponentScan({"repository"})
@ComponentScan({"service.before_after_mapper"})
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    private final ConsultationMapper consultationMapper;
    private final IBeforeAfterMapper<ConsultationCreationDto, Consultation> consultationCreationBeforeAfterMapper;
    private final IBeforeAfterMapper<ConsultationUpdateDto, Consultation> consultationUpdateBeforeAfterMapper;

    public ConsultationService(
            ConsultationRepository consultationRepository,
            UserRepository userRepository,
            PatientRepository patientRepository,
            ConsultationMapper consultationMapper,
            IBeforeAfterMapper<ConsultationCreationDto, Consultation> consultationCreationBeforeAfterMapper,
            IBeforeAfterMapper<ConsultationUpdateDto, Consultation> consultationUpdateBeforeAfterMapper) {

        this.consultationRepository = consultationRepository;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.consultationMapper = consultationMapper;
        this.consultationCreationBeforeAfterMapper = consultationCreationBeforeAfterMapper;
        this.consultationUpdateBeforeAfterMapper = consultationUpdateBeforeAfterMapper;
    }

    public ConsultationDto create(ConsultationCreationDto consultationCreationDto) throws Exception {
        Consultation consultation = consultationRepository.save(
                consultationCreationBeforeAfterMapper.after(
                        consultationCreationDto,
                        consultationMapper.consultationCreationDtoToConsultation(consultationCreationDto)));

        return consultationMapper.consultationToConsultationDto(consultation);
    }

    public void update(ConsultationUpdateDto consultationUpdateDto) throws Exception {
        consultationRepository.save(
                consultationUpdateBeforeAfterMapper.after(
                        consultationUpdateDto,
                        consultationMapper.consultationUpdateDtoToConsultation(consultationUpdateDto)));
    }

    public void delete(int consultationId) {
        consultationRepository.deleteById(consultationId);
    }

    public Optional<ConsultationDto> getById(int id) {
        return consultationRepository
                .findById(id)
                .map(consultationMapper::consultationToConsultationDto);
    }

    public Optional<ConsultationDto> getByPatientId(int patientId) {
        return consultationRepository
                .findConsultationByPatient_Id(patientId)
                .map(consultationMapper::consultationToConsultationDto);
    }
}
