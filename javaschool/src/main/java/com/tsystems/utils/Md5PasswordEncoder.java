package com.tsystems.utils;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Performs encode operations
 */
public class Md5PasswordEncoder implements PasswordEncoder {

    private Md5PasswordEncoder() {
    }

    /**
     * Encodes password from char sequence
     *
     * @param charSequence charSequence
     * @return encoded password string
     */
    @Override
    public String encode(CharSequence charSequence) {
        String password = charSequence.toString();
        return HashPasswordUtil.getHash(password);
    }

    /**
     * Checks matching charSequence and string
     *
     * @param charSequence charSequence
     * @param s s
     * @return true if equals, false otherwise
     */
    @Override
    public boolean matches(CharSequence charSequence, String s) {
        String hashedInput = encode(charSequence);
        if (hashedInput.equals(s)) {
            return true;
        }
        return false;
    }
}
