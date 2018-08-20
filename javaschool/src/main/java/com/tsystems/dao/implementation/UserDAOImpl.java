package com.tsystems.dao.implementation;

import com.tsystems.dao.api.UserDAO;
import com.tsystems.entity.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;

/**
 * An implementation of UserDAO api
 */
@Repository
public class UserDAOImpl extends GenericDAOImpl<User, Integer> implements UserDAO {

    private static final Logger log = Logger.getLogger(UserDAOImpl.class);

    /**
     * Finds and returns user by id
     *
     * @param id id
     * @return user found by id
     */
    public User findById(Integer id) {
        Query findUser = entityManager.createQuery("select user from User user where user.id=:id");
        findUser.setParameter("id", id);
        return (User) findUser.getSingleResult();
    }

    /**
     * Finds and returns user by username
     *
     * @param username username
     * @return user found by username
     */
    @Transactional
    public User findByUsername(String username) {
        Query findByUsername = entityManager.createQuery("select user from User user where user.username=:username");
        findByUsername.setParameter("username", username);
        if (findByUsername.getResultList().size() > 0) {
            return (User) findByUsername.getResultList().get(0);
        } else {
            return null;
        }
    }

    /**
     * Finds and returns user by email
     *
     * @param email email
     * @return user found by email
     */
    public User findByEmail(String email) {
        Query findByEmail = entityManager.createQuery("select user from User user where user.email=:email");
        findByEmail.setParameter("email", email);
        if (findByEmail.getResultList().size() > 0) {
            return (User) findByEmail.getResultList().get(0);
        } else {
            return null;
        }
    }

}
