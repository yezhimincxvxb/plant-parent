package com.moguying.plant.core.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.moguying.plant.core.service.mapper")
public class MybatisPlusConfig {
}
