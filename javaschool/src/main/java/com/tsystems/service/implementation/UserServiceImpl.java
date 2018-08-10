package com.tsystems.service.implementation;

import com.tsystems.dao.api.UserDAO;
import com.tsystems.entity.User;
import com.tsystems.entity.enums.Role;
import com.tsystems.exceptions.RegisterFailedException;
import com.tsystems.service.api.UserService;
import com.tsystems.utils.HashPasswordUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    private static final Logger log = Logger.getLogger(UserServiceImpl.class);

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    public void addUser(User user, String birthday) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date parsed = format.parse(birthday);
            user.setBirthday(new java.sql.Date(parsed.getTime()));
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        user.setRole(Role.USER);
        user.setPassword(HashPasswordUtil.getHash(user.getPassword()));
        User foundByUsername = userDAO.findByUsername(user.getUsername());
        if (foundByUsername != null) {
            throw new RegisterFailedException("User with such username already exist.");
        }
        User foundByEmail = userDAO.findByEmail(user.getEmail());
        if (foundByEmail != null) {
            throw new RegisterFailedException("User with such E-mail already exist.");
        }
        userDAO.add(user);
        log.info("User was successfully registered: " + user.toString());
    }

    @Transactional
    public User getUser(String username) {
        return userDAO.findByUsername(username);
    }

    @Transactional
    public User findById(Integer id) {
        return userDAO.findById(id);
    }

}
