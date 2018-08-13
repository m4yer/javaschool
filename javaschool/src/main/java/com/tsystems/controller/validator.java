package com.tsystems.controller;

import java.util.regex.Pattern;

public class validator {

    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z]+[a-zA-Z0-9._]+@[a-zA-Z]+\\.[a-zA-Z.]{2,5}$");

    public static boolean isValid(String expression, Pattern pattern) {
        return pattern.matcher(expression).matches() ? true : false;
    }

}
