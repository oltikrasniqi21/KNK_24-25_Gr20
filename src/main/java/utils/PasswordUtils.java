package utils;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtils {

    public static String getSalt(){
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] hashed = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashed);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static boolean checkPasswordMatch(String newVal, String oldPassword){
        String[] parts = oldPassword.split("\\$");
        if(parts.length !=2) return false;

        String salt = parts[0];
        String oldHash = parts[1];

        String inputHash = PasswordUtils.hashPassword(newVal, salt);
        return oldHash.equals(inputHash);
    }
}