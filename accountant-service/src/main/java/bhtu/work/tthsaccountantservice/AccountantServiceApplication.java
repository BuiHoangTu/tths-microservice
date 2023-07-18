package bhtu.work.tthsaccountantservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
@EnableEurekaClient
public class AccountantServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountantServiceApplication.class, args);
	}

}
