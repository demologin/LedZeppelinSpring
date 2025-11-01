package com.javarush.led.lesson05;

import com.javarush.led.lesson05.entity.User;
import com.javarush.led.lesson05.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Lesson05Application {

    public static void main(String[] args) {
        var context = SpringApplication.run(Lesson05Application.class, args);
        UserService userService = context.getBean(UserService.class);

        for (long id = 1; id <= 3; id++) {
            User user = userService.getById(id);
            log.warn("data {}", user);
        }
        User alisa = userService.getByUsername("Alisa");
        log.warn("Alisa data {}", alisa);
    }

}
