package com.rewe.distributor.service.impl;

import com.rewe.distributor.dto.EmailDto;
import com.rewe.distributor.message.producer.EmailProducer;
import com.rewe.distributor.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailProducer emailProducer;

    public void sendEmail(EmailDto email) {
        log.info("Start sending email");
        emailProducer.send(email);
        log.info("End sending email");
    }

    public void sendRandomEmail(String topic) {
        log.info("Start sending random email");
        EmailDto emailDto = generateRandomEmail(topic);
        emailProducer.send(emailDto);
        log.info("End sending random email");
    }

    private EmailDto generateRandomEmail(String topic) {
        PodamFactory factory = new PodamFactoryImpl();
        EmailDto emailDto = factory.manufacturePojo(EmailDto.class);
        emailDto.setTopic(topic);
        return emailDto;
    }
}
