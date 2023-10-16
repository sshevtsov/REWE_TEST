package com.rewe.distributor.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Map;

/*
 * Class for statistics of received emails
 * */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmailStatisticDto {

    private LocalDate date;
    private Map<String, Long> domainCount;

}
