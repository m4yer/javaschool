package com.tsystems.controller;

import com.tsystems.entity.User;
import com.tsystems.exceptions.RegisterFailedException;
import com.tsystems.service.api.SecurityService;
import com.tsystems.service.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller is responsible for registration processes
 */
@Controller
public class RegisterController {
    private UserService userService;
    private SecurityService securityService;

    @Autowired
    public RegisterController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    private static final Logger log = Logger.getLogger(RegisterController.class);

    @GetMapping("/register")
    public String registerPage(@ModelAttribute("user") User user, BindingResult result) {
        return "register";
    }

    @PostMapping("/register")
    public String addUser(HttpServletRequest request, @ModelAttribute("user") User user, BindingResult result, @RequestParam("birthday") String birthday) throws RegisterFailedException {
        String userPassword = user.getPassword();
        userService.addUser(user, birthday);
        securityService.autoLogin(request, user.getUsername(), userPassword);
        return "redirect:/";
    }

}
