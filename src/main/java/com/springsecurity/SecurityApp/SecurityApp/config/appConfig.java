package com.springsecurity.SecurityApp.SecurityApp.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class appConfig {

    @Bean
    ModelMapper getmapper(){
        return new ModelMapper();
    }
}
