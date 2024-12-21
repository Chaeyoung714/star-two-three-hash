package miniproject.star_two_three;

import miniproject.star_two_three.util.HashConverter;
import org.junit.jupiter.api.Test;

public class HashConverterTest {

    @Test
    void 해시값_생성_테스트() {
        Long data = 12345L;
        String hashedData = HashConverter.convertLongValue(data);
        System.out.println(hashedData);
    }

}
