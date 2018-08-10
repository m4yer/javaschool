package com.tsystems.service.api;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {

    void autoLogin(HttpServletRequest request, String username, String password);

}
