package miniproject.star_two_three.util;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HashDecoder {

    //TODO : 수정
    private static final byte[] SECRET_KEY = "1234567890123456".getBytes();


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
