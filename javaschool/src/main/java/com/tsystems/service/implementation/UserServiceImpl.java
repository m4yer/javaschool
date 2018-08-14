package com.tsystems.service.implementation;

import com.tsystems.controller.validation.Validator;
import com.tsystems.dao.api.UserDAO;
import com.tsystems.dto.UserDTO;
import com.tsystems.entity.User;
import com.tsystems.entity.converter.Converter;
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
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    private static final Logger log = Logger.getLogger(UserServiceImpl.class);

    @Transactional
    public void addUser(User user, String birthday) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date parsed = format.parse(birthday);
            user.setBirthday(new java.sql.Date(parsed.getTime()));
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            throw new RegisterFailedException();
        }
        if (user.getUsername().length() < 4 || user.getUsername() == null ||
            user.getPassword().length() < 6 || user.getPassword() == null ||
            user.getFirstname() == null || user.getLastname() == null ||
                !Validator.isValid(user.getEmail(), Validator.EMAIL_PATTERN) ||
            userDAO.findByUsername(user.getUsername()) != null ||
            userDAO.findByEmail(user.getEmail()) != null) {
            throw new RegisterFailedException();
        }
        user.setRole(Role.USER);
        user.setPassword(HashPasswordUtil.getHash(user.getPassword()));
        userDAO.add(user);
        log.info("User was successfully registered: " + user.toString());
    }

    @Transactional
    public UserDTO findByUsername(String username) {
        return Converter.getUserDto(userDAO.findByUsername(username));
    }

    @Transactional
    public UserDTO findByEmail(String email) {
        return Converter.getUserDto(userDAO.findByEmail(email));
    }

    @Transactional
    public UserDTO findById(Integer id) {
        return Converter.getUserDto(userDAO.findById(id));
    }

    @Transactional
    public List<UserDTO> getAllUsers() {
        return Converter.getUserDtos(userDAO.getAll());
    }

}
