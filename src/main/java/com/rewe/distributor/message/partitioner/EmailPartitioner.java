package com.rewe.distributor.message.partitioner;

import com.rewe.distributor.dto.EmailDto;
import com.rewe.distributor.domain.EmailDomainMapping;
import com.rewe.distributor.util.EmailValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.InvalidRecordException;

import java.util.Map;
import java.util.Objects;

import static com.rewe.distributor.domain.EmailDomainMapping.*;

/*
 * Custom Partitioner which determines which partition to send the message to
 * */
@Slf4j
public class EmailPartitioner implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        if (valueBytes == null || !(value instanceof EmailDto email)) {
            log.error("Message was not sent due to incorrect type");
            throw new InvalidRecordException("Message is not valid");
        }

        String senderEmail = email.getSender();

        if (!EmailValidator.validate(senderEmail)) {
            log.error("Incorrect email address: {}", senderEmail);
            throw new RuntimeException("Incorrect email address: " + senderEmail);
        }

        String emailDomain = senderEmail.split("@")[1];
        EmailDomainMapping emailDomainMapping = valueOfName(emailDomain);

        if (Objects.isNull(emailDomainMapping)) {
            log.error("There is no partitioning for such a domain: {}", emailDomain);
            throw new RuntimeException("There is no partitioning for such a domain: " + emailDomain);
        }

        return switch (emailDomainMapping) {
            case GOOGLE -> GOOGLE.getPartition();
            case MICROSOFT -> MICROSOFT.getPartition();
            case AMAZON -> AMAZON.getPartition();
        };
    }

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> map) {
    }
}
