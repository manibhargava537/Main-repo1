package com.smartcrop.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordGenerator {

    public static final int PASSWORD_LENGTH = 10;
    public static final int EMPID_LENGTH = 6;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static final String PASSWORD_CHARS= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&?";
    public static final String EMPID_CHARS = "0123456789";
    public static String generateRandomPassword(int length, String allowedChars) {
        char[] possibleCharacters = (new String(allowedChars)).toCharArray();
        String randomStr = RandomStringUtils.random( length, 0, possibleCharacters.length-1, false, false, possibleCharacters, new SecureRandom() );
        System.out.println( randomStr );
        return randomStr;
    }

    public String getBCryptPasswordEncoder(String plainTextPassword) {
        return passwordEncoder.encode(plainTextPassword);
    }
}
