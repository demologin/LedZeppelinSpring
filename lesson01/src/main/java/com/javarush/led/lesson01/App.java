package com.javarush.led.lesson01;

import com.javarush.led.lesson01.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-cfg.xml");
        UserService userService = context.getBean(UserService.class);
        System.out.println(userService);
    }
}
