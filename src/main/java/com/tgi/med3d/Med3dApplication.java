package com.tgi.med3d;

import org.jasypt.digest.PooledStringDigester;
import org.jasypt.digest.StringDigester;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.tgi.med3d.model.RoleMaster;
import com.tgi.med3d.repository.RoleMasterRepository;

@SpringBootApplication
public class Med3dApplication {

	public static void main(String[] args) {
		SpringApplication.run(Med3dApplication.class, args);  
	}
	

}
