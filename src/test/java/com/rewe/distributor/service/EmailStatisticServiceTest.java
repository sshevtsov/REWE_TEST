package com.rewe.distributor.service;

import com.rewe.distributor.domain.EmailStatistic;
import com.rewe.distributor.dto.EmailDto;
import com.rewe.distributor.dto.EmailStatisticDto;
import com.rewe.distributor.repository.EmailStatisticRepository;
import com.rewe.distributor.service.impl.EmailStatisticServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.rewe.distributor.domain.EmailDomainMapping.AMAZON;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailStatisticServiceTest {

    @InjectMocks
    private EmailStatisticServiceImpl statisticService;
    @Mock
    private EmailStatisticRepository statisticRepository;

    @Test
    public void get_statistic_test() {
        EmailStatistic emailStatistic = new EmailStatistic();
        emailStatistic.setEmailDomain(AMAZON.name());
        emailStatistic.setReceiveDate(LocalDate.now());

        when(statisticRepository.findAll()).thenReturn(List.of(emailStatistic));

        List<EmailStatisticDto> statistic = statisticService.getStatistic();

        Assertions.assertEquals(1, statistic.size());

        EmailStatisticDto statisticDto = statistic.get(0);
        Map<String, Long> domainCount = statisticDto.getDomainCount();

        Assertions.assertEquals(LocalDate.now(), statisticDto.getDate());
        Assertions.assertEquals(1, domainCount.get(AMAZON.name()));

    }

    @Test
    public void save_statistic_test() {
        when(statisticRepository.save(any())).thenReturn(any());

        EmailDto emailDto = new EmailDto();
        emailDto.setSender("test@gmail.com");

        statisticService.saveStatistic(emailDto);
    }

    @Test
    public void save_statistic_with_incorrect_email_test() {
        String emailSender = "testgmail.com";
        EmailDto emailDto = new EmailDto();
        emailDto.setSender(emailSender);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            statisticService.saveStatistic(emailDto);
        });

        String expectedMessage = "Statistic not saved due incorrect sender email: " + emailSender;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
