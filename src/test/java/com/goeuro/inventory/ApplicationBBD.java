package com.goeuro.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootApplication
public class ApplicationBBD {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationBBD.class, args);
    }
}
