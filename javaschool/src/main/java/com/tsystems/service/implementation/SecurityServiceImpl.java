package com.tsystems.service.implementation;

import com.tsystems.service.api.SecurityService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Service
public class SecurityServiceImpl implements SecurityService {
    private static final Logger log = Logger.getLogger(SecurityServiceImpl.class);

    /**
     * Method for auto-logging
     *
     * @param request request
     * @param username username
     * @param password password
     */
    @Override
    public void autoLogin(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            log.error(e);
        }
    }

}
