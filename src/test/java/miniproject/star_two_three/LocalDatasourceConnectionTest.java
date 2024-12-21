package miniproject.star_two_three;

import java.sql.Connection;
import javax.sql.DataSource;
import miniproject.star_two_three.domain.Member;
import miniproject.star_two_three.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocalDatasourceConnectionTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void testH2Connection() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            Assertions.assertThat(connection).isNotNull();
            System.out.println("H2 Database connected successfully: " + connection.getMetaData().getURL());
        }
    }

    @Test
    void 테스트데이터베이스_연결확인() {
        Member testMember = new Member("testId", "testNickname", "1541");

        memberRepository.save(testMember);

        Assertions.assertThat(memberRepository.findByLoginId("testId")).isEqualTo(testMember);
    }
}
