package miniproject.star_two_three.security.util;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;

public class HashDecoder {

    @Value("${spring.password.secret-key}")
    private static String secretKey;
    private static final byte[] SECRET_KEY = secretKey.getBytes();


    public static String decrypt(String encryptedValue) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new IllegalStateException("unexpected exception during decoding");
        }
    }
}
