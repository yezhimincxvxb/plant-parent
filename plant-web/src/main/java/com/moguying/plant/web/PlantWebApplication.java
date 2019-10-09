package com.moguying.plant.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.moguying"})
public class PlantWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlantWebApplication.class, args);
    }

}
