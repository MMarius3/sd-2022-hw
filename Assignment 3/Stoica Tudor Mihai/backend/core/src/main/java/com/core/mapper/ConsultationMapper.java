package com.core.mapper;

import com.core.dto.ConsultationDto;
import com.infrastructure.entity.Consultation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {

    Consultation consultationDtoToConsultation(ConsultationDto consultationDto);

    ConsultationDto consultationToConsultationDto(Consultation consultation);
}
