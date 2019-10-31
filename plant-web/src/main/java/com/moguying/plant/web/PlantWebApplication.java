package com.moguying.plant.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.moguying"})
@EnableTransactionManagement
@EnableScheduling
public class PlantWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlantWebApplication.class, args);
    }

}
