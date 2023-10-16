package com.rewe.distributor.message.producer;

import com.rewe.distributor.config.ApplicationProperties;
import com.rewe.distributor.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/*
 * Email producer who sends email to Kafka
 * */
@Slf4j
@Component
@RequiredArgsConstructor
public class EmailProducer {

    private final KafkaTemplate<String, EmailDto> kafkaTemplate;
    private final ApplicationProperties applicationProperties;

    public void send(EmailDto email) {
        final ApplicationProperties.ConsumerTopic topic = applicationProperties.getTopic();
        kafkaTemplate.send(topic.getName(), email);
        log.info("Email was sent to Kafka");
    }

}
