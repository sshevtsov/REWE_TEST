package com.rewe.distributor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
@EnableAutoConfiguration(exclude = KafkaAutoConfiguration.class)
public class DistributorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributorApplication.class, args);
	}

}
