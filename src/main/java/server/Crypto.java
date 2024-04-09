package server;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Crypto {

    private static final String AES_SECRET_KEY = "DefaultSecretKey";

    public static String UUIDv4 () {
        return UUID.randomUUID().toString();
    }
    public static String encrypt(String s) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(AES_SECRET_KEY.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(s.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            return null;
        }
    }

    public static String decrypt(String s) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(AES_SECRET_KEY.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(s));
            return new String(decryptedBytes);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean compare(String encrypted, String plain) {
        return Crypto.decrypt(encrypted).equals(plain);
    }
}
