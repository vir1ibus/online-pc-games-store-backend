package org.vir1ibus.onlinestore.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public class Cryptography {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private static final String encryptAlgorithm = "SHA-512";

    public static String generatorString(int length) {
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes).replaceAll("=", "");
    }

    public static String[] encrypt(String input) {
        try {
            String salt = generatorString(10);
            return new String[]{
                    base64Encoder.encodeToString(
                            MessageDigest.getInstance(encryptAlgorithm).digest(
                                    (input + salt).getBytes(StandardCharsets.UTF_8)
                            )
                    ),
                    salt};
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String input, String salt) {
        try {
            return base64Encoder.encodeToString(
                    MessageDigest.getInstance(encryptAlgorithm).digest(
                            (input + salt).getBytes(StandardCharsets.UTF_8)
                    )
            );
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
