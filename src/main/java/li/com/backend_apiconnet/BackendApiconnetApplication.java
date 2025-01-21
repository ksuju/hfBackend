package li.com.backend_apiconnet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackendApiconnetApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApiconnetApplication.class, args);
	}

}
