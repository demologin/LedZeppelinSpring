package com.javarush.lesson23;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class Lesson23SpringCloudConfig {

    public static void main(String[] args) {
        SpringApplication.run(Lesson23SpringCloudConfig.class, args);
    }

}
