package com.tsystems.service.api;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {

    /**
     * Method for auto-logging
     *
     * @param request request
     * @param username username
     * @param password password
     */
    void autoLogin(HttpServletRequest request, String username, String password);

}
