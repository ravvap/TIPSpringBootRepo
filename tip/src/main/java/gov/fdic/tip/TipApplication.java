package gov.fdic.tip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


/**
 * @author Prasad Ravva
 * @Project TIP
 * @Module Review Cycle Group
 * @Date 9/28/2025
 * 	Description: Main class to start the Spring Boot application. 		
 */

@SpringBootApplication(scanBasePackages = "gov.fdic.tip")
public class TipApplication {

	public static void main(String[] args) {
		SpringApplication.run(TipApplication.class, args);
	}

}
