package com.tsystems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Dispatches queries related to home page or denied page
 */
@Controller
public class HomeController {

    /**
     * Returns the index page.
     *
     * @return index.jsp
     */
    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    /**
     * Returns the 403 page.
     *
     * @return error/403.jsp
     */
    @GetMapping("/denied")
    public String deniedPage() {
        return "error/403";
    }

}
