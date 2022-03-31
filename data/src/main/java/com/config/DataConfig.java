package com.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan({"com.adapter"})
@EntityScan("com.entity")
@EnableJpaRepositories("com.repository")
@PropertySource(value = "classpath:data.yml", factory = YamlPropertySourceFactory.class)
public class DataConfig {
}
