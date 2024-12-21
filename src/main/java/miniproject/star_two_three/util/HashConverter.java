package miniproject.star_two_three.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashConverter {

    public static String convertStringValue(String value) {
        try {
            // MD5(128비트) 해시 생성
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(value.getBytes());

            // 해시 값을 16진수 문자열로 변환하여 리턴
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("no such hash algorithm");
        }
    }

    public static String convertLongValue(Long value){
        try {
            byte[] valueBytes = longToBytes(value);

            // MD5(128비트) 해시 생성
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(valueBytes);

            // 해시 값을 16진수 문자열로 변환하여 리턴
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("no such hash algorithm");
        }
    }

    // Long 값을 바이트 배열로 변환하는 메소드
    private static byte[] longToBytes(Long value) {
        byte[] bytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte) (value >>> (56 - (i * 8)));
        }
        return bytes;
    }

    // 바이트 배열을 16진수 문자열로 변환하는 메소드
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
