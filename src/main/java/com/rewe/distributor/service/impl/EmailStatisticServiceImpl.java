package com.rewe.distributor.service.impl;

import com.rewe.distributor.domain.EmailDomainMapping;
import com.rewe.distributor.domain.EmailStatistic;
import com.rewe.distributor.dto.EmailDto;
import com.rewe.distributor.dto.EmailStatisticDto;
import com.rewe.distributor.repository.EmailStatisticRepository;
import com.rewe.distributor.service.EmailStatisticService;
import com.rewe.distributor.util.EmailValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.rewe.distributor.domain.EmailDomainMapping.valueOfName;
import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailStatisticServiceImpl implements EmailStatisticService {

    private final EmailStatisticRepository statisticRepository;

    public List<EmailStatisticDto> getStatistic() {
        log.info("Start getting statistic");
        List<EmailStatistic> statistics = statisticRepository.findAll();

        Map<LocalDate, Map<String, Long>> collectedStatistic = statistics.stream()
                .collect(groupingBy(EmailStatistic::getReceiveDate,
                groupingBy(EmailStatistic::getEmailDomain, Collectors.counting())));

        List<EmailStatisticDto> statisticDtos = collectedStatistic.entrySet().stream()
                .map(s -> new EmailStatisticDto(s.getKey(), s.getValue()))
                .collect(Collectors.toList());

        log.info("End getting statistic");

        return statisticDtos;
    }

    public void saveStatistic(EmailDto email) {
        if (EmailValidator.validate(email.getSender())) {
            String emailDomain = email.getSender().split("@")[1];
            EmailDomainMapping emailDomainMapping = valueOfName(emailDomain);
            if (!Objects.isNull(emailDomainMapping)) {
                EmailStatistic emailStatistic = new EmailStatistic();
                emailStatistic.setReceiveDate(LocalDate.now());
                emailStatistic.setEmailDomain(emailDomainMapping.name());
                log.info("Start saving statistic");
                statisticRepository.save(emailStatistic);
                log.info("End saving statistic");
            }
        } else {
            log.error("Statistic not saved due incorrect sender email: {}", email.getSender());
            throw new RuntimeException("Statistic not saved due incorrect sender email: " + email.getSender());
        }
    }
}
