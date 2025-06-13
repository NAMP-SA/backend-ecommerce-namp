package com.namp.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EcommerceApplication {

    public static void main(String[] args) {SpringApplication.run(EcommerceApplication.class, args);
    }

}
