package com.tsystems.controller.validation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/admin/user/list")
    public String allUsersPage() {
        return "admin/user_list";
    }

}
