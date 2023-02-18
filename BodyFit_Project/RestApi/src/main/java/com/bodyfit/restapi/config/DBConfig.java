package com.bodyfit.restapi.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.bodyfit.restapi.model.dao")
public class DBConfig {
}
