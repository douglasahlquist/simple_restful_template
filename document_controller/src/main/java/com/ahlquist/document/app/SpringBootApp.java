package com.ahlquist.document.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

@Configuration
@SpringBootApplication
@EnableConfigurationProperties()
@ComponentScan({"com.ahlquist.document.config", "com.ahlquist.document.ex.services", "com.ahlquist.document.services", 
	"com.ahlquist.document.model", "com.ahlquist.document.repositories" })
public class SpringBootApp  {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class,args);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
