package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProducerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ProducerApplication.class, args);
        ProducerService producerService = (ProducerService)run.getBean("producerService");
        producerService.sendMessage();
    }
}
