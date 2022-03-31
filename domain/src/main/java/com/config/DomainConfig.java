package com.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({"com.service", "com.security"})
@PropertySource("classpath:security.yml")
public class DomainConfig {
}
