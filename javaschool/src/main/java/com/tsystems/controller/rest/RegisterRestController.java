package com.tsystems.controller.rest;

import com.tsystems.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dispatches the registration related rest-queries
 */
@RestController
public class RegisterRestController {
    private UserService userService;

    @Autowired
    public RegisterRestController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Checks if there is already registered specific username
     *
     * @param username username
     * @return true if such username already registered, false otherwise
     */
    @GetMapping("/register/is-allowed/username")
    public boolean isUsernameRegistered(@RequestParam("username") String username) {
        try {
            return userService.findByUsername(username) == null ? true : false;
        } catch (NullPointerException npe) {
            return true;
        }
    }

    /**
     * Checks if there is already registered specific email
     *
     * @param email email
     * @return true if such email already registered, false otherwise
     */
    @GetMapping("/register/is-allowed/email")
    public boolean isEmailRegistered(@RequestParam("email") String email) {
        String checkEmail = email.replaceAll("%40", "@");
        try {
            return userService.findByEmail(checkEmail) == null ? true : false;
        } catch (NullPointerException npe) {
            return true;
        }
    }

}
