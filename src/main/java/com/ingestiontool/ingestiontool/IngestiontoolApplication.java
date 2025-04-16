package com.ingestiontool.ingestiontool;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IngestiontoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(IngestiontoolApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(ClickHouseService clickHouseService) {
        return args -> {
            clickHouseService.testConnection();
        };
    }
}
