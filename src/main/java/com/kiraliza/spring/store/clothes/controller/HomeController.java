package com.kiraliza.spring.store.clothes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController
{
    @GetMapping("/error")
    public String errorPage()
    {
        return "error";
    }
}
