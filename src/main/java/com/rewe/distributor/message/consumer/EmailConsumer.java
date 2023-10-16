package com.rewe.distributor.message.consumer;

import com.rewe.distributor.dto.EmailDto;
import com.rewe.distributor.service.impl.EmailStatisticServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/*
 * Email consumer which listens to email Kafka topic
 * */
@Slf4j
@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailStatisticServiceImpl statisticService;

    @KafkaListener(topics = "${config.topic.name}")
    public void listen(@Payload EmailDto email,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        log.debug("Received email: {}, from partition: {}", email, partition);
        statisticService.saveStatistic(email);
    }
}
