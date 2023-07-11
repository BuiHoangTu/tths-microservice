package bhtu.work.tthsaccountantservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
public class AccountantServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountantServiceApplication.class, args);
	}

}
