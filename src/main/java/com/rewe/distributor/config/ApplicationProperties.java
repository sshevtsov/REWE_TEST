package com.rewe.distributor.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "config")
public class ApplicationProperties {

    private List<String> partitionFilter;
    private ConsumerTopic topic;

    @Getter
    @Setter
    public static class ConsumerTopic {
        private String name;
        private int partition;
        private int replication;
    }
}
