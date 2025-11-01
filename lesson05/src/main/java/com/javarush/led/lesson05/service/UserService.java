package com.javarush.led.lesson05.service;

import com.javarush.led.lesson05.config.AppConfig;
import com.javarush.led.lesson05.entity.User;
import com.javarush.led.lesson05.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@ToString
public class UserService {
    private AppConfig appConfig;
    private final UserRepository userRepository;

    public User getById(Long id) {
        System.out.println(appConfig);
        return userRepository.findById(id).orElseThrow();
    }

    public User getByUsername(String login) {
        return userRepository.findByLogin(login).orElseThrow();
    }

}
