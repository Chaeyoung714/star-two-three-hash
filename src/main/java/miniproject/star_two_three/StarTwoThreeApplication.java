package miniproject.star_two_three;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StarTwoThreeApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
				.directory("./")
				.load();

		System.setProperty("RDS_URL", dotenv.get("RDS_URL"));
		System.setProperty("RDS_USERNAME", dotenv.get("RDS_USERNAME"));
		System.setProperty("RDS_PASSWORD", dotenv.get("RDS_PASSWORD"));

		SpringApplication.run(StarTwoThreeApplication.class, args);
	}

}
