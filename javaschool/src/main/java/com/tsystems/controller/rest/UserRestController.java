package com.tsystems.controller.rest;

import com.tsystems.service.api.UserService;
import com.tsystems.utils.ConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dispatches the user related rest-queries
 */
@RestController
public class UserRestController {
    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Returns all users
     *
     * @return list of all users in JSON
     */
    @GetMapping("/admin/user/list/get")
    public @ResponseBody
    String getAllUsers() {
        return ConverterUtil.parseJson(userService.getAllUsers());
    }

}
