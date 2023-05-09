package info.zpss.test.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class PasswordEncrypter {
    private static final int KEY_SIZE = 128;

    public static SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(KEY_SIZE, new SecureRandom());
        return keyGenerator.generateKey();
    }

    public static byte[] encryptPassword(String password, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
    }

    public static String decryptPassword(byte[] encryptedPassword, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedPassword = cipher.doFinal(encryptedPassword);
        return new String(decryptedPassword, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws Exception {
        SecretKey key = generateSecretKey();
        String password = "123456";
        byte[] encryptedPassword = encryptPassword(password, key);
        String decryptedPassword = decryptPassword(encryptedPassword, key);
        System.out.println("password: " + decryptedPassword);
    }
}
