package com.rewe.distributor;

import com.rewe.distributor.domain.EmailDomainMapping;
import com.rewe.distributor.domain.EmailStatistic;
import com.rewe.distributor.dto.EmailDto;
import com.rewe.distributor.repository.EmailStatisticRepository;
import com.rewe.distributor.service.EmailService;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Collectors.groupingBy;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

/*
 * End-to-end test without mocks
 * */
@DirtiesContext
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestDistributorApplication.class)
@EnableAutoConfiguration(exclude = {KafkaAutoConfiguration.class})
public class E2ETestIT {

    @Autowired
    private EmailService emailService;
    @Autowired
    private EmailStatisticRepository statisticRepository;
    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @Test
    public void e2e_test() {
        EmailDto email1 = createEmail("test-email1@microsoft.com");
        EmailDto email2 = createEmail("test-email2@microsoft.com");
        EmailDto email3 = createEmail("test-email1@gmail.com");
        EmailDto email4 = createEmail("test-email2@gmail.com");
        EmailDto email5 = createEmail("test-email1@amazon.com");
        EmailDto email6 = createEmail("test-email2@amazon.com");

        emailService.sendEmail(email1);
        emailService.sendEmail(email2);
        emailService.sendEmail(email3);
        emailService.sendEmail(email4);
        emailService.sendEmail(email5);
        emailService.sendEmail(email6);

        await().during(Duration.ofSeconds(10))
                .atMost(Duration.ofSeconds(20))
                .pollDelay(Duration.ofSeconds(2))
                .until(() -> statisticRepository.findAll().size() == 6);

        List<EmailStatistic> statistics = statisticRepository.findAll();

        assertEquals(6, statistics.size());

        Map<String, Long> groupedStatistic = statistics.stream()
                .collect(groupingBy(EmailStatistic::getEmailDomain, Collectors.counting()));

        assertEquals(2L, (long) groupedStatistic.get(EmailDomainMapping.AMAZON.name()));
        assertEquals(2L, (long) groupedStatistic.get(EmailDomainMapping.MICROSOFT.name()));
        assertEquals(2L, (long) groupedStatistic.get(EmailDomainMapping.GOOGLE.name()));
    }

    @Test
    public void e2e_test_with_random_email() {
        emailService.sendRandomEmail("topic1");
        emailService.sendRandomEmail("topic2");
        emailService.sendRandomEmail("topic3");
        emailService.sendRandomEmail("topic4");
        emailService.sendRandomEmail("topic5");

        await().until(() -> statisticRepository.findAll().size() == 5);

        List<EmailStatistic> statistics = statisticRepository.findAll();

        assertEquals(5, statistics.size());

        statisticRepository.deleteAll();
    }

    @Test
    public void e2e_test_with_incorrect_email() {
        String emailSender = "test-email1microsoft.com";
        EmailDto email = createEmail(emailSender);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            emailService.sendEmail(email);
        });

        String expectedMessage = "Incorrect email address: " + emailSender;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    private EmailDto createEmail(String emailAddress) {
        EmailDto email = new EmailDto();
        email.setTopic("TEST TOPIC");
        email.setContent("TEST CONTENT");
        email.setSender(emailAddress);
        email.setRecipients(List.of("recipients1@mail.com", "recipients2@mail.com"));
        return email;
    }
}

