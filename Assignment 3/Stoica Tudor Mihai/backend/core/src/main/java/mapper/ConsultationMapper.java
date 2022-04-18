package mapper;

import dto.ConsultationDto;
import entity.Consultation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {

    Consultation consultationDtoToConsultation(ConsultationDto consultationDto);

    ConsultationDto consultationToConsultationDto(Consultation consultation);
}
