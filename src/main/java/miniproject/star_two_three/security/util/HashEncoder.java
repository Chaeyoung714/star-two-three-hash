package miniproject.star_two_three.security.util;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HashEncoder {

    @Value("${spring.password.secret-key}")
    private String secretKey;

    public String encryptLongValue(Long value){
        try {
            byte[] SECRET_KEY = secretKey.getBytes();
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(value.toString().getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new IllegalStateException("unexpected exception during encoding");
        }
    }
}
