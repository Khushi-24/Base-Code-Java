package com.demo.BaristaBucks.Security.Encryption;

import com.demo.BaristaBucks.Log.LogHolder;
import com.demo.BaristaBucks.Log.LogKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static net.logstash.logback.marker.Markers.appendEntries;

public class Encryption {
    public static String sha256(String input)/* throws NoSuchAlgorithmException */{
        MessageDigest mDigest = null;
        try {
            mDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
        }
        byte[] hash= mDigest.digest(input.getBytes());
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static final Logger log = LoggerFactory.getLogger(Encryption.class);
    private static final String INIT_VECTOR = "gZJE27GkxzBnDLnD";

    public static String encrypt(String value, String privateKey) {
        try {
            //for logging
            LogHolder logData = new LogHolder();
            logData.put(LogKeys.DECRYPTED_RESPONSE, value);
            log.info(appendEntries(logData.getAttributes()), "before encryption");
//            MDC.put(LogKeys.DECRYPTED_RESPONSE, value);
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec skeySpec = new
                    SecretKeySpec(privateKey
                    .getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            byte[] original = Base64.getEncoder().encode(encrypted);
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String encrypted, String privateKey) {
        try {
            LogHolder logHolder = new LogHolder();
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR
                    .getBytes(StandardCharsets.UTF_8));
            SecretKeySpec skeySpec = new SecretKeySpec(privateKey.
                    getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
            LogHolder logData = new LogHolder();
            logData.put(LogKeys.DECRYPTED_REQUEST, new String(original));
            log.info(appendEntries(logData.getAttributes()), "after decrypt");
//            MDC.put(LogKeys.DECRYPTED_REQUEST, new String(original));
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

}

