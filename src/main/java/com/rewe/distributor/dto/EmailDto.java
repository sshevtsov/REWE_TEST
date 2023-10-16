package com.rewe.distributor.dto;

import com.rewe.distributor.util.EmailStrategy;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.co.jemos.podam.common.PodamStrategyValue;

import java.util.List;

/*
 * Object for presenting email data
 * */
@Getter
@Setter
@ToString
public class EmailDto {
    private String topic;
    @PodamStrategyValue(EmailStrategy.class)
    private String sender;
    private String content;
    private List<String> recipients;
}
