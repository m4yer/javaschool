package com.tsystems.dao.api;

import com.tsystems.entity.User;

/**
 * CRUD and specific operations
 */
public interface UserDAO extends GenericDAO<User, Integer> {

    /**
     * Finds and returns user by username
     *
     * @param username username
     * @return user found by username
     */
    User findByUsername(String username);

    /**
     * Finds and returns user by email
     *
     * @param email email
     * @return user found by email
     */
    User findByEmail(String email);

    /**
     * Finds and returns user by id
     *
     * @param id id
     * @return user found by id
     */
    User findById(Integer id);

}
