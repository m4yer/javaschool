package com.tsystems.service.api;

import com.tsystems.dto.UserDTO;
import com.tsystems.entity.User;

import java.util.List;

public interface UserService {

    /**
     * Adds new user
     *
     * @param user user
     * @param birthday birthday
     */
    void addUser(User user, String birthday);

    /**
     * Finds and returns user by username
     *
     * @param username username
     * @return user found by username
     */
    UserDTO findByUsername(String username);

    /**
     * Finds and returns user by email
     *
     * @param email email
     * @return user found by email
     */
    UserDTO findByEmail(String email);

    /**
     * Finds and returns user by id
     *
     * @param id id
     * @return user found by id
     */
    UserDTO findById(Integer id);

    /**
     * Returns all users
     *
     * @return list of users
     */
    List<UserDTO> getAllUsers();

}
