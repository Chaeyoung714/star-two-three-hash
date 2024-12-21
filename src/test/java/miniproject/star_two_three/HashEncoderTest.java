package miniproject.star_two_three;

import miniproject.star_two_three.util.HashEncoder;
import org.junit.jupiter.api.Test;

public class HashEncoderTest {

    @Test
    void 해시값_생성_테스트() {
        Long data = 12345L;
        String hashedData = HashEncoder.encryptLongValue(data);
        System.out.println(hashedData);
    }

}
