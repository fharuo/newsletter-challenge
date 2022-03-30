package com.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EntityScan("com.entities")
@PropertySource(value = "classpath:data.yml", factory = YamlPropertySourceFactory.class)
public class DataConfig {
}
