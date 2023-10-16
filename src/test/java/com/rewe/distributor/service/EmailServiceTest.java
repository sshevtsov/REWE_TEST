package com.rewe.distributor.service;

import com.rewe.distributor.dto.EmailDto;
import com.rewe.distributor.message.producer.EmailProducer;
import com.rewe.distributor.service.impl.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @InjectMocks
    private EmailServiceImpl emailService;
    @Mock
    private EmailProducer emailProducer;

    @Test
    public void send_email_test() {
        doNothing().when(emailProducer).send(any());
        emailService.sendEmail(new EmailDto());
    }

    @Test
    public void send_random_email_test() {
        doNothing().when(emailProducer).send(any());
        emailService.sendRandomEmail("test_topic");
    }
}
