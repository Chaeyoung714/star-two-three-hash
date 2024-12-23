package miniproject.star_two_three.security.util;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HashEncoder {

    //TODO : 변환
    private static final byte[] SECRET_KEY = "1234567890123456".getBytes();


    public static String encryptLongValue(Long value){
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(value.toString().getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new IllegalStateException("unexpected exception during encoding");
        }
    }

//    // Long 값을 바이트 배열로 변환하는 메소드
//    private static byte[] longToBytes(Long value) {
//        byte[] bytes = new byte[8];
//        for (int i = 0; i < 8; i++) {
//            bytes[i] = (byte) (value >>> (56 - (i * 8)));
//        }
//        return bytes;
//    }
//
//    // 바이트 배열을 16진수 문자열로 변환하는 메소드
//    private static String bytesToHex(byte[] bytes) {
//        StringBuilder hexString = new StringBuilder();
//        for (byte b : bytes) {
//            hexString.append(String.format("%02x", b));
//        }
//        return hexString.toString();
//    }
}
