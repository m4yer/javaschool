package com.tsystems.utils;

import org.springframework.security.crypto.password.PasswordEncoder;

public class Md5PasswordEncoder implements PasswordEncoder {

    private Md5PasswordEncoder() {
    }

    @Override
    public String encode(CharSequence charSequence) {
        String password = charSequence.toString();
        return HashPasswordUtil.getHash(password);
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        String hashedInput = encode(charSequence);
        if (hashedInput.equals(s)) {
            return true;
        }
        return false;
    }
}
