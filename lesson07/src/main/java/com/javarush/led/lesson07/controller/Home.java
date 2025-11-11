package com.javarush.led.lesson07.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Home {

    @RequestMapping("/")
    String home(){
       return "redirect:/user/list";
    }
}
