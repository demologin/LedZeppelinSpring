package com.javarush.led.lesson10.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    @GetMapping("/")
    public String showHomePage() {
        return "redirect:/products/create";
    }

    @GetMapping("/products/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    @PostMapping("/products/create")
    public String createProduct(
            @Valid Product product,
            Model model) {
//        if (bindingResult.hasErrors()) {
//            return "product-form";
//        }

        // Logic to save the product
        // System.out.println("Product saved: " + product.getName());

        return "redirect:/products/success";
    }

    @GetMapping("/product/gift")
    public String showGift() {
        throw new ResourceNotFoundException("Not found gift");
    }

    @GetMapping("/products/success")
    public String showSucces(Model model) {
        model.addAttribute("product", new Product());
        return "product-success";
    }

}