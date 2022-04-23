package com.application.controller_test;

import com.application.controller.SecretaryController;
import com.core.dto.consultation.ConsultationCreationDto;
import com.core.dto.consultation.ConsultationDto;
import com.core.dto.patient.PatientDto;
import com.core.service.ConsultationService;
import com.core.service.PatientService;
import com.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static com.application.controller.UrlMappings.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @BeforeEach
    protected void setUp() {
        super.setUp();
        secretaryController = new SecretaryController(consultationService, patientService);
        mockMvc = MockMvcBuilders.standaloneSetup(secretaryController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void createConsultation() throws Exception {

        LocalDateTime defaultDate = LocalDateTime.now();

        PatientDto patientDto =
                new PatientDto()
                        .setId(1)
                        .setName("name")
                        .setAddress("address")
                        .setBirthDate(defaultDate)
                        .setPersonalNumericalCode("1234567890123");

        patientService.createPatient(patientDto);

        ConsultationCreationDto consultationCreationDto =
                new ConsultationCreationDto()
                        .setId(1)
                        .setPatientId(1)
                        .setDoctorId(1)
                        .setDate(defaultDate)
                        .setDetails("details");

        ConsultationDto consultationDto =
                new ConsultationDto()
                        .setId(1)
                        .setDate(defaultDate)
                        .setDetails("details")
                        .setDoctor(userService.getById(1).orElse(null))
                        .setPatient(patientService.getPatientById(1).orElse(null));

        when(consultationService.create(consultationCreationDto)).thenReturn(consultationDto);

        performPostWithRequestBody(CREATE_CONSULTATION, consultationCreationDto)
                .andExpect(status().isOk())
                .andExpect(jsonContentToBe(consultationDto));
    }
}
