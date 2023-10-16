package com.rewe.distributor.web;

import com.rewe.distributor.dto.EmailStatisticDto;
import com.rewe.distributor.service.EmailStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * Controller for email statistic
 * */
@RestController
@RequestMapping("email")
@RequiredArgsConstructor
public class StatisticController {

    private final EmailStatisticService statisticService;

    @GetMapping("/statistic")
    public ResponseEntity<List<EmailStatisticDto>> getStatistic() {
        return ResponseEntity.ok(statisticService.getStatistic());
    }
}
