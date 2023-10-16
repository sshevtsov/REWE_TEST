package com.rewe.distributor;

import com.rewe.distributor.config.kafka.KafkaProducerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestDistributorApplication {

	public static void main(String[] args) {
		SpringApplication.from(DistributorApplication::main).with(TestDistributorApplication.class).run(args);
	}

}
