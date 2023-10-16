package com.rewe.distributor.messafe.partitioner;

import com.rewe.distributor.TestDistributorApplication;
import com.rewe.distributor.config.ApplicationProperties;
import com.rewe.distributor.domain.EmailDomainMapping;
import com.rewe.distributor.dto.EmailDto;
import com.rewe.distributor.message.consumer.EmailConsumer;
import com.rewe.distributor.message.producer.EmailProducer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/*
* Test correct partitioner by email domain, using custom KafkaConsumer
* */
@DirtiesContext
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestDistributorApplication.class)
@EnableAutoConfiguration(exclude = {KafkaAutoConfiguration.class})
@Import(EmailPartitionerTestIT.KafkaTestContainersConfiguration.class)
public class EmailPartitionerTestIT {
    @MockBean
    private EmailConsumer consumer;
    @Autowired
    private EmailProducer producer;
    @Autowired
    private KafkaConsumer<String, EmailDto> testConsumer;
    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @Test
    public void test_all_partition() {
        EmailDto email1 = createEmail("test-email1@microsoft.com");
        EmailDto email2 = createEmail("test-email2@microsoft.com");
        EmailDto email3 = createEmail("test-email1@gmail.com");
        EmailDto email4 = createEmail("test-email2@gmail.com");
        EmailDto email5 = createEmail("test-email1@amazon.com");
        EmailDto email6 = createEmail("test-email2@amazon.com");

        producer.send(email1);
        producer.send(email2);
        producer.send(email3);
        producer.send(email4);
        producer.send(email5);
        producer.send(email6);

        ConsumerRecords<String, EmailDto> records = testConsumer
                .poll(Duration.ofSeconds(100L));

        assertEquals(6, records.count());

        records.forEach(record -> {
            EmailDto email = record.value();
            String emailDomain = email.getSender().split("@")[1];
            EmailDomainMapping domainMapping = EmailDomainMapping.valueOfName(emailDomain);
            assertNotNull(domainMapping);
            assertEquals(domainMapping.getPartition(), record.partition());
        });
    }

    private EmailDto createEmail(String emailAddress) {
        EmailDto email = new EmailDto();
        email.setTopic("TEST TOPIC");
        email.setContent("TEST CONTENT");
        email.setSender(emailAddress);
        email.setRecipients(List.of("recipients1@mail.com", "recipients2@mail.com"));
        return email;
    }

    @TestConfiguration
    static class KafkaTestContainersConfiguration {
        @Autowired
        private ApplicationProperties applicationProperties;

        @Bean
        Consumer<String, EmailDto> testConsumer() {
            Map<String, Object> props = new HashMap<>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
            props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group-id");
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            Consumer<String, EmailDto> consumer =
                    new DefaultKafkaConsumerFactory<>(props,
                            new StringDeserializer(), new JsonDeserializer<>(EmailDto.class)).createConsumer();

            consumer.subscribe(Collections.singletonList(applicationProperties.getTopic().getName()));
            return consumer;
        }
    }
}

