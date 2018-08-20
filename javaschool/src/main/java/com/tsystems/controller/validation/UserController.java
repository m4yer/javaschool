package com.tsystems.controller.validation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Dispatches the user related queries
 */
@Controller
public class UserController {

    /**
     * Returns page of all users
     *
     * @return admin/user_list.jsp
     */
    @GetMapping("/admin/user/list")
    public String allUsersPage() {
        return "admin/user_list";
    }

}
