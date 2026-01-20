package com.javarush.lesson24;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Lesson24Application {

    public static void main(String[] args) {
        SpringApplication.run(Lesson24Application.class, args);
        System.out.println("Hello Eureka! http://localhost:8761/");
    }

}
