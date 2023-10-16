package com.rewe.distributor.service;

import com.rewe.distributor.dto.EmailDto;
import com.rewe.distributor.dto.EmailStatisticDto;

import java.util.List;

/*
 * Email statistic service
 * */
public interface EmailStatisticService {
    /**
     * Get all structured email statistic
     * @return list of email statistic {@link EmailStatisticDto}
     */
    List<EmailStatisticDto> getStatistic();
    /**
     * Save statistic for email
     * @param  email {@link EmailDto}
     */
    void saveStatistic(EmailDto email);
}
