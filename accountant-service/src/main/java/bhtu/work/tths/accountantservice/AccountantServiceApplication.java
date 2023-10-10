package bhtu.work.tths.accountantservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication()
@EnableEurekaClient
public class AccountantServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountantServiceApplication.class, args);
	}

}
