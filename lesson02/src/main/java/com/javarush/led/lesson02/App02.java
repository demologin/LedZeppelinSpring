package com.javarush.led.lesson02;

import com.javarush.led.lesson02.config.Config;
import com.javarush.led.lesson02.entity.User;
import com.javarush.led.lesson02.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App02 {


    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        UserService userService = context.getBean(UserService.class);
        User[] users={
                new User(1L,"admin","pass","ADMIN"),
                new User(3L,"guest","asda","GUEST"),
                new User(2L,"user","qweqweqweqweqweqweqweqeqweqweqweqwe","USER"),
        };
        userService.save(users);
        System.out.println(userService);
    }
}
