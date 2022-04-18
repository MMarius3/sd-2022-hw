//package service;
//
//import dto.ConsultationDto;
//import mapper.ConsultationMapper;
//import org.springframework.stereotype.Service;
//import repository.ConsultationRepository;
//
//import java.util.Optional;
//
//@Service
//public class ConsultationService {
//
//    private final ConsultationRepository consultationRepository;
//    private final ConsultationMapper consultationMapper;
//
//    public ConsultationService(ConsultationRepository consultationRepository, ConsultationMapper consultationMapper) {
//        this.consultationRepository = consultationRepository;
//        this.consultationMapper = consultationMapper;
//    }
//
//    public void create(ConsultationDto consultationDto) {
//        consultationRepository.save(consultationMapper.consultationDtoToConsultation(consultationDto));
//    }
//
//    public void delete(int id) {
//        consultationRepository.deleteById(id);
//    }
//
//    public void update(ConsultationDto consultationDto) {
//        consultationRepository.save(consultationMapper.consultationDtoToConsultation(consultationDto));
//    }
//
//    public Optional<ConsultationDto> getById(int id) {
//        return consultationRepository
//                .findById(id)
//                .map(consultationMapper::consultationToConsultationDto);
//    }
//}
