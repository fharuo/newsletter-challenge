package com.config;

import com.security.JWTAuthFilter;
import com.security.JWTService;
import com.service.serviceImpl.SubscriberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SubscriberServiceImpl subscriberService;
    private final JWTService jwtService;

    public SecurityConfig(SubscriberServiceImpl subscriberService, JWTService jwtService) {
        this.subscriberService = subscriberService;
        this.jwtService = jwtService;
    }

    @Bean
    public OncePerRequestFilter jwtFilter() { return new JWTAuthFilter(jwtService, subscriberService); }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(subscriberService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/v1/subscribers")
                   .authenticated()
                .antMatchers(HttpMethod.POST,"/api/v1/subscribers")
                    .authenticated()
                .antMatchers("/api/v1/subscribers/auth/**")
                   .permitAll()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
