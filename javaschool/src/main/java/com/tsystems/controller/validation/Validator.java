package com.tsystems.controller.validation;

import java.util.regex.Pattern;

/**
 * Class for EMail validation
 */
public class Validator {

    private Validator() {
    }

    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z]+[a-zA-Z0-9._]+@[a-zA-Z]+\\.[a-zA-Z.]{2,5}$");

    /**
     * Checks if specified expression is valid by specified pattern
     *
     * @param expression expression to check
     * @param pattern pattern
     * @return true if expression valid, false otherwise
     */
    public static boolean isValid(String expression, Pattern pattern) {
        return pattern.matcher(expression).matches();
    }

}
