package com.tsystems.service.implementation;

import com.tsystems.dao.api.UserDAO;
import com.tsystems.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username);
        String peshes = "Peshes";
        System.out.println("user: " + user.toString());

        if (user == null) {
            throw new UsernameNotFoundException("User wasn't found");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        return new com.tsystems.service.implementation.UserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                grantedAuthorities,
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getBirthday());
    }

}
