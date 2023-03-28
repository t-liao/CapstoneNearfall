package com.example.nearfall.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

// Implementation of salted hash passwords for data storage
public class HashedPassword {
    private final String hashedPassword;
    private final byte[] salt;

    public HashedPassword(String password) throws NoSuchAlgorithmException {
        this.salt = generateSalt();
        this.hashedPassword = computeHashedPassword(password, this.salt);
    }

    public HashedPassword(String password, byte[] salt, boolean isHashed) throws NoSuchAlgorithmException {
        if (isHashed) {
            this.hashedPassword = password;
        } else {
            this.hashedPassword = computeHashedPassword(password, salt);
        }
        this.salt = salt;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    public byte[] getSalt() {
        return this.salt;
    }

    public String computeHashedPassword(String nonHashedPassword, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(salt);
            byte[] bytes = digest.digest(nonHashedPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        return bytes;
    }
}
