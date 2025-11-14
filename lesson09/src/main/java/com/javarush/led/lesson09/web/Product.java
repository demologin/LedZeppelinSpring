package com.javarush.led.lesson09.web;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Product {
    @NotBlank(message = "Product name is required.")
    private String name;

    @DecimalMin(value = "0.01", message = "Price must be greater than 0.")
    private double price;
    
}