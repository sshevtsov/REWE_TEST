package com.rewe.distributor.repository;

import com.rewe.distributor.domain.EmailStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Repository for email statistic
 * */
@Repository
public interface EmailStatisticRepository extends JpaRepository<EmailStatistic, Long> {
}
