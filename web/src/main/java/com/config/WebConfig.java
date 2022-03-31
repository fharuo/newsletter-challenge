package com.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DataConfig.class, DomainConfig.class})
@ComponentScan({"com.controller"})
public class WebConfig {
}
