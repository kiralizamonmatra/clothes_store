package com.kiraliza.spring.store.clothes.controller;

import com.kiraliza.spring.store.clothes.controller.handler.CustomLogoutHandler;
import com.kiraliza.spring.store.clothes.helper.LogHelper;
import com.kiraliza.spring.store.clothes.service.UserService;
import com.kiraliza.spring.store.clothes.type.Routes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController
{
    @Autowired
    private UserService userService;

    @GetMapping(Routes.LOGIN)
    public String login()
    {
        return "login";
    }

    @GetMapping(Routes.LOGOUT)
    public String logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response)
    {
        LogHelper.info("============LOGOUT");
        new CustomLogoutHandler().logout(request, response, authentication);
        return "redirect:" + Routes.HOME;
    }
}
