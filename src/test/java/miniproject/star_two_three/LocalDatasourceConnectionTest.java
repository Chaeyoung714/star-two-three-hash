package miniproject.star_two_three;

import java.sql.Connection;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocalDatasourceConnectionTest {
    @Autowired
    private DataSource dataSource;

    @Test
    void testH2Connection() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            Assertions.assertThat(connection).isNotNull();
            System.out.println("H2 Database connected successfully: " + connection.getMetaData().getURL());
        }
    }
}
