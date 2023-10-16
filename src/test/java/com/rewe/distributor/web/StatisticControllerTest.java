package com.rewe.distributor.web;

import com.rewe.distributor.domain.EmailDomainMapping;
import com.rewe.distributor.dto.EmailStatisticDto;
import com.rewe.distributor.service.EmailStatisticService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StatisticController.class)
public class StatisticControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmailStatisticService statisticService;

    @Test
    public void get_statistic_test() throws Exception {
        Map<String, Long> domainCount = new HashMap<>();
        domainCount.put(EmailDomainMapping.AMAZON.getName(), 3L);
        EmailStatisticDto statisticDto = new EmailStatisticDto(LocalDate.now(), domainCount);

        when(statisticService.getStatistic()).thenReturn(List.of(statisticDto));

        this.mockMvc.perform(get("/email/statistic")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].date").value(statisticDto.getDate().toString()));
    }
}
