package com.email;

import com.coin.CoinMapper;
import com.coin.CoinRepository;
import com.coin.CoinService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailSenderServiceTest {

    @InjectMocks
    private EmailSenderService emailSenderService;

    @Mock
    private JavaMailSender mailSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        emailSenderService = new EmailSenderService();
    }

    @Test
    void sendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marius.macean@gmail.com");
        message.setTo("yommane@gmail.com");
        message.setSubject("New Coin");
        message.setText("Coin was just added to the website!");
        emailSenderService.sendEmail("yommane@gmail.com", "New Coin", "Coin was just added to the website!");
        verify(mailSender, times(1)).send(message);
    }
}