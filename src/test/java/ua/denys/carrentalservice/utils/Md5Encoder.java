package ua.denys.carrentalservice.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Encoder {
    private static Md5Encoder INSTANCE = null;

    private Md5Encoder() {

    }

    public static Md5Encoder getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new Md5Encoder();
        return INSTANCE;
    }

    public String encodeToMD5(String Text) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] messageDigest = md.digest(Text.getBytes());
        return new BigInteger(messageDigest).toString(16);
    }
}
