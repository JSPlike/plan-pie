package hanco.planpie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "hanco.planpie.user.domain")
public class PlanpieApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanpieApplication.class, args);
	}

}
