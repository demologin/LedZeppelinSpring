package com.javarush.lesson18;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@Slf4j
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ApplicationWithDbAuth {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationWithDbAuth.class, args);
        log.info("http://localhost:8080/");
    }
}
