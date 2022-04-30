package com.core.service.before_after_mapper;

import com.core.dto.consultation.ConsultationUpdateDto;
import com.infrastructure.entity.Consultation;
import org.springframework.stereotype.Service;

@Service
public class ConsultationUpdateDtoBeforeAfterMapper implements IBeforeAfterMapper<ConsultationUpdateDto, Consultation>{

    @Override
    public Consultation before(ConsultationUpdateDto consultationUpdateDto) {
        return null;
    }

    @Override
    public Consultation after(ConsultationUpdateDto consultationUpdateDto, Consultation partiallyMappedDestination) {
        return null;
    }
}
