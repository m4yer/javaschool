package com.tsystems.controller;

import com.tsystems.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(@ModelAttribute User user, BindingResult result) {
        return "login";
    }

}
