package com.moguying.plant.core.config;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableSwagger2Doc
@Profile("test")
public class SwaggerConfig {
}
