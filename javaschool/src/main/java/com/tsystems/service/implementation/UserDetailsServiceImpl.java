package com.tsystems.service.implementation;

import com.tsystems.entity.User;
import com.tsystems.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUser(username);
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
