package com.javarush.led.lesson02.service;

import com.javarush.led.lesson02.entity.User;
import com.javarush.led.lesson02.repository.UserRepository;
import com.javarush.led.lesson02.validator.Validator;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@ToString
public class UserService {
    private final UserRepository userRepository;
    private final List<Validator<User>> validators;


//    private final Validator<User> validator2;

    public void save(User... users) {
        for (User user : users) {
            validators.forEach(validator -> validator.validate(user));
            userRepository.save(user);
        }
    }
}
