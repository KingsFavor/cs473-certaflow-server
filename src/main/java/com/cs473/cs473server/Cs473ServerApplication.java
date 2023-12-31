package com.cs473.cs473server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Cs473ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Cs473ServerApplication.class, args);
    }

}
