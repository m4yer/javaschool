package com.tsystems.controller.rest;

import com.tsystems.service.api.UserService;
import com.tsystems.utils.ConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/user/list/get")
    public @ResponseBody String getAllUsers() {
        return ConverterUtil.parseJson(userService.getAllUsers());
    }

}
