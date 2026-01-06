package com.javarush.lesson20;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ApplicationWithViktoriaMetrics {
    public static void main(String[] args) {
        var context = SpringApplication.run(ApplicationWithViktoriaMetrics.class, args);
        System.out.println();
    }
}
