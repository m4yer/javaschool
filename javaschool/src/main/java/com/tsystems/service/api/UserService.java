package com.tsystems.service.api;

import com.tsystems.dto.UserDTO;
import com.tsystems.entity.User;

import java.util.List;

public interface UserService {

    void addUser(User user, String birthday);

    UserDTO findByUsername(String username);

    UserDTO findByEmail(String email);

    UserDTO findById(Integer id);

    List<UserDTO> getAllUsers();

}
