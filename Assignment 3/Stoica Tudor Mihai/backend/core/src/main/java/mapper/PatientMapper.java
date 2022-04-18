package mapper;

import dto.PatientDto;
import dto.UserDto;
import entity.Patient;
import entity.User;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    Patient patientDtoToPatient(PatientDto patientDto);

    PatientDto patientToPatientDto(Patient patient);
}
