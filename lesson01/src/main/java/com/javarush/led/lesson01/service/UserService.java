package com.javarush.led.lesson01.service;

import com.javarush.led.lesson01.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class UserService {
    private final UserRepository userRepository;
}
