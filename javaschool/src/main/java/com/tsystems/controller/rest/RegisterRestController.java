package com.tsystems.controller.rest;

import com.tsystems.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterRestController {
    private UserService userService;

    @Autowired
    public RegisterRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register/is-allowed/username")
    public boolean isUsernameRegistered(@RequestParam("username") String username) {
        try {
            return userService.findByUsername(username) == null ? true : false;
        } catch (NullPointerException npe) { return true; }
    }

    @GetMapping("/register/is-allowed/email")
    public boolean isEmailRegistered(@RequestParam("email") String email) {
        String checkEmail = email.replaceAll("%40", "@");
        try {
            return userService.findByEmail(checkEmail) == null ? true : false;
        } catch (NullPointerException npe) { return true; }
    }

}
