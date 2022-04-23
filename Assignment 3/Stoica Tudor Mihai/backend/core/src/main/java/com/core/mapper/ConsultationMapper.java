package com.core.mapper;

import com.core.dto.consultation.ConsultationCreationDto;
import com.core.dto.consultation.ConsultationDto;
import com.core.dto.consultation.ConsultationUpdateDto;
import com.infrastructure.entity.Consultation;
import com.infrastructure.repository.PatientRepository;
import com.infrastructure.repository.UserRepository;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserRepository.class, PatientRepository.class})
public abstract class ConsultationMapper {

    public abstract Consultation consultationDtoToConsultation(ConsultationDto consultationDto);

    public abstract ConsultationDto consultationToConsultationDto(Consultation consultation);

    public abstract Consultation consultationCreationDtoToConsultation(ConsultationCreationDto consultationCreationDto);

    public abstract Consultation consultationUpdateDtoToConsultation(ConsultationUpdateDto consultationUpdateDto);
}
