package com.javarush.led.lesson01;

import com.javarush.led.lesson01.config.ApplicationProperties;
import com.javarush.led.lesson01.config.SessionCreator;
import com.javarush.led.lesson01.repository.UserRepository;
import com.javarush.led.lesson01.service.UserService;

public class App {
    public static void main(String[] args) {
        ApplicationProperties applicationProperties = new ApplicationProperties();
        SessionCreator sessionCreator = new SessionCreator(applicationProperties);
        UserService userService = new UserService(new UserRepository(sessionCreator));
        System.out.println(userService);
    }
}
