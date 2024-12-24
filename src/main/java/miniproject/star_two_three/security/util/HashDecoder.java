package miniproject.star_two_three.security.util;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import miniproject.star_two_three.exception.CustomException;
import miniproject.star_two_three.exception.Exceptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HashDecoder {

    @Value("${spring.password.secret-key}")
    private String secretKey;


    public String decrypt(String encryptedValue) {
        try {
            byte[] SECRET_KEY = secretKey.getBytes();
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = cipher.doFinal(Base64.getUrlDecoder().decode(encryptedValue));
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new CustomException(Exceptions.INVALID_ROOM_SIGNATURE);
        }
    }
}
