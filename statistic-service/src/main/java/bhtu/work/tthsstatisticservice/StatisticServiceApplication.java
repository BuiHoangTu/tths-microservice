package bhtu.work.tthsstatisticservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
public class StatisticServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StatisticServiceApplication.class, args);
	}

}
