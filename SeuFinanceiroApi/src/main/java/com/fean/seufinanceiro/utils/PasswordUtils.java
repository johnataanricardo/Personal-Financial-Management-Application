package com.fean.seufinanceiro.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

    public PasswordUtils() {
    }

    /**
     * Generate a hash using BCrypt.
     *
     * @param password
     * @return String
     */
    public static String generateBCrypt(String password) {
        if (password == null) {
            return password;
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }
}

