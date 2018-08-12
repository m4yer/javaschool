package com.tsystems.controller;

import com.tsystems.service.implementation.MailService;
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

@Controller
public class RegisterController {

    private UserService userService;
    private SecurityService securityService;
    private MailService mailService;

    @Autowired
    public RegisterController(UserService userService, SecurityService securityService, MailService mailService) {
        this.userService = userService;
        this.securityService = securityService;
        this.mailService = mailService;
    }

    private static final Logger log = Logger.getLogger(RegisterController.class);

    @GetMapping("/register")
    public String registerPage(@ModelAttribute("user") User user, BindingResult result) {
        return "register";
    }

    @PostMapping("/register")
    public String addUser(HttpServletRequest request, @ModelAttribute("user") User user, BindingResult result, @RequestParam("birthday") String birthday) {
        String userPassword = user.getPassword();
        try {
            mailService.sendMail("from@no-spam.ru",
                    "aspid888@gmail.com", "Important information", "Hey, new user has just registered!\nUsername: " + user.getUsername());

            userService.addUser(user, birthday);
            securityService.autoLogin(request, user.getUsername(), userPassword);
            return "redirect:/";
        } catch (RegisterFailedException e) {
            log.error(e.getMessage(), e);
            return "redirect:/register?status=error";
        }
    }

}
