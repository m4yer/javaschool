package com.tsystems.service.api;

import com.tsystems.entity.User;

public interface UserService {

    void addUser(User user, String birthday);

    User getUser(String username);

    User findById(Integer id);

}
