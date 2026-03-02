package com.thishotel.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Password encoding utility.
 */
public class PasswordUtil {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encodePassword(String password){
        return encoder.encode(password);
    }
}
