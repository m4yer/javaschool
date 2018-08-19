package com.tsystems.controller;

import com.tsystems.entity.User;
import com.tsystems.service.implementation.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.function.Predicate;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(@ModelAttribute User user, BindingResult result) {
        return "login";
    }

    @GetMapping("/login-forward")
    public String processLoginForwarding() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Predicate<GrantedAuthority> roleAdmin = grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN");
        return userDetails.getAuthorities().stream().anyMatch(roleAdmin) ? "redirect:/admin/trip/list" : "redirect:/";
    }

}
