package dev.umc.healody;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "dev.umc.healody.home")
public class HealodyApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealodyApplication.class, args);
	}

}
