package miniproject.star_two_three;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocalDatasourceConnectionTest {
    @Autowired
    private DataSource dataSource;

    @BeforeAll
    static void setUp() {
        Dotenv dotenv = Dotenv.configure()
                .directory("./")
                .load();
        System.setProperty("RDS_URL", dotenv.get("RDS_URL"));
        System.setProperty("RDS_USERNAME", dotenv.get("RDS_USERNAME"));
        System.setProperty("RDS_PASSWORD", dotenv.get("RDS_PASSWORD"));
        System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));
        System.setProperty("PASSWORD_SECRET_KEY", dotenv.get("PASSWORD_SECRET_KEY"));
    }

    @Test
    void testH2Connection() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println(connection.getMetaData());
            Assertions.assertThat(connection).isNotNull();
            System.out.println("H2 Database connected successfully: " + connection.getMetaData().getURL());
        }
    }


}
