package com.rewe.distributor.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/*
 * Object for presenting statistics table
 * */
@Entity
@Getter
@Setter
@Table(name = "EMAIL_STATISTIC")
public class EmailStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email_domain")
    private String emailDomain;
    @Column(name = "receive_date")
    private LocalDate receiveDate;

}

