package ru.nvkz.http.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {
        //return "redirect:" + request.getHeader("referer");
        return "user/login";
    }

}