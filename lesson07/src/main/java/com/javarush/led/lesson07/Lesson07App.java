package com.javarush.led.lesson07;

import com.javarush.led.lesson07.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Lesson07App {

    public static void main(String[] args) {
        var context = SpringApplication.run(Lesson07App.class, args);
        UserService userService = context.getBean(UserService.class);


    }

}
