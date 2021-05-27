package com.tgi.med3d.configuration;

import java.time.Duration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig
{
	@Value("${readTimeout}")
	private String readTimeout;
	@Value("${connectionTimeout}")
	private String connectionTimeout;

    @Bean 
    public RestTemplate restTemplate(
            RestTemplateBuilder restTemplateBuilder) {

        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(Integer.parseInt(connectionTimeout)))
                .setReadTimeout(Duration.ofSeconds(Integer.parseInt(readTimeout)))
                .build();
    }
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}