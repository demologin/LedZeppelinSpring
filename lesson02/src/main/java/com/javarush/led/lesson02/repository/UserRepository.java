package com.javarush.led.lesson02.repository;

import com.javarush.led.lesson02.config.SessionCreator;
import com.javarush.led.lesson02.entity.User;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
@ToString
public class UserRepository {
    private final SessionCreator sessionCreator;

    public void save(User user) {
        System.out.println(user + " saved to database");
    }
}
