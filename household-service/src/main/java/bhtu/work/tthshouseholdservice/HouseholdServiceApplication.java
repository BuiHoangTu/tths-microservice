package bhtu.work.tthshouseholdservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
public class HouseholdServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HouseholdServiceApplication.class, args);
	}

}
