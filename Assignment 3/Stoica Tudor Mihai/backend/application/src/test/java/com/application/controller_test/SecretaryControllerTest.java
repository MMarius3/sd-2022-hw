package com.application.controller_test;

import com.application.controller.SecretaryController;
import com.application.controller.UrlMappings;
import com.core.dto.consultation.ConsultationCreationDto;
import com.core.dto.consultation.ConsultationDto;
import com.core.dto.patient.PatientDto;
import com.core.dto.user.UserDto;
import com.core.service.ConsultationService;
import com.core.service.PatientService;
import com.core.service.UserService;
import com.core.service.doctor_notification.IUserNotifier;
import com.infrastructure.entity.UserType;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.application.controller.UrlMappings.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan(basePackages = {"com.core"})
@ComponentScan(basePackages = {"com.infrastructure"})
@ComponentScan(basePackages = {"com.application"})
@RequiredArgsConstructor
public class SecretaryControllerTest extends BaseControllerTest {

    @InjectMocks
    private SecretaryController secretaryController;

    @Mock
    private ConsultationService consultationService;
    @Mock
    private PatientService patientService;
    @Mock
    private UserService userService;
    @Mock
    private IUserNotifier doctorNotificationService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        secretaryController = new SecretaryController(consultationService, patientService, doctorNotificationService);
        mockMvc = MockMvcBuilders.standaloneSetup(secretaryController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void createConsultation() throws Exception {

        LocalDateTime defaultDate = LocalDateTime.now();
        int patientId = 1;
        int doctorId = 1;

        PatientDto patientDto =
                new PatientDto()
                        .setId(patientId)
                        .setName("name")
                        .setAddress("address")
                        .setBirthDate(defaultDate)
                        .setPersonalNumericalCode("1234567890123");

        patientService.createPatient(patientDto);

        UserDto userDto =
                new UserDto()
                        .setId(doctorId)
                        .setName("nameeee")
                        .setUserType(UserType.DOCTOR)
                        .setPassword("asdkojeoiwq")
                        .setEmail("asdsa@gmail.com");

        userService.create(userDto);

        ConsultationCreationDto consultationCreationDto =
                new ConsultationCreationDto()
                        .setPatientId(patientId)
                        .setDoctorId(doctorId)
                        .setDate(defaultDate)
                        .setDetails("details");

        ConsultationDto consultationDto =
                new ConsultationDto()
                        .setDate(defaultDate)
                        .setDetails("details")
                        .setDoctor(userService.getById(doctorId).orElse(null))
                        .setPatient(patientService.getPatientById(patientId).orElse(null));

        when(consultationService.create(consultationCreationDto)).thenReturn(consultationDto);

        performPostWithRequestBody(CREATE_CONSULTATION, consultationCreationDto)
                .andExpect(status().isOk())
                .andExpect(jsonContentToBe(consultationDto));
    }

    @Test
    public void getConsultationsByPatientId() throws Exception {

        LocalDateTime defaultDate = LocalDateTime.now();
        int patientId = 1;
        int doctorId = 1;
        int consultationId = 1;

        PatientDto patientDto =
                new PatientDto()
                        .setId(patientId)
                        .setName("name")
                        .setAddress("address")
                        .setBirthDate(defaultDate)
                        .setPersonalNumericalCode("1234567890123");

        patientService.createPatient(patientDto);

        UserDto userDto =
                new UserDto()
                        .setId(doctorId)
                        .setName("nameeee")
                        .setUserType(UserType.DOCTOR)
                        .setPassword("asdkojeoiwq")
                        .setEmail("asdsa@gmail.com");

        userService.create(userDto);

        ConsultationCreationDto consultationCreationDto =
                new ConsultationCreationDto()
                        .setId(consultationId)
                        .setPatientId(patientId)
                        .setDoctorId(doctorId)
                        .setDate(defaultDate)
                        .setDetails("details");

        ConsultationDto consultationDto =
                new ConsultationDto()
                        .setDate(defaultDate)
                        .setDetails("details")
                        .setDoctor(userService.getById(doctorId).orElse(null))
                        .setPatient(patientService.getPatientById(patientId).orElse(null));

        consultationService.create(consultationCreationDto);

        when(consultationService.getByPatientId(consultationId)).thenReturn(Optional.ofNullable(consultationDto));

        ResultActions result = performGetWithPathVariable(UrlMappings.GET_CONSULTATIONS_BY_PATIENT_ID, patientId);
        result
                .andExpect(status().isOk())
                .andExpect(jsonContentToBe(consultationDto));
    }

    @Test
    public void update() {
        LocalDateTime defaultDate = LocalDateTime.now();
        int patientId = 1;
        int doctorId = 1;
        int consultationId = 1;

        PatientDto patientDto =
                new PatientDto()
                        .setId(patientId)
                        .setName("name")
                        .setAddress("address")
                        .setBirthDate(defaultDate)
                        .setPersonalNumericalCode("1234567890123");

        patientService.createPatient(patientDto);

        UserDto userDto =
                new UserDto()
                        .setId(doctorId)
                        .setName("nameeee")
                        .setUserType(UserType.DOCTOR)
                        .setPassword("asdkojeoiwq")
                        .setEmail("asdsa@gmail.com");

        userService.create(userDto);

        ConsultationCreationDto consultationCreationDto =
                new ConsultationCreationDto()
                        .setId(consultationId)
                        .setPatientId(patientId)
                        .setDoctorId(doctorId)
                        .setDate(defaultDate)
                        .setDetails("details");

        ConsultationDto consultationDto =
                new ConsultationDto()
                        .setDate(defaultDate)
                        .setDetails("details")
                        .setDoctor(userService.getById(doctorId).orElse(null))
                        .setPatient(patientService.getPatientById(patientId).orElse(null));
    }
}
