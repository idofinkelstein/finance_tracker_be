package com.ido.financetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FinanceTrackerApplication {

	public static void main(String[] args) {
		// Set default profile to dev if no profile is specified
		if (System.getProperty("spring.profiles.active") == null) {
			// set java system properties
			System.setProperty("spring.profiles.active", "dev");
		}
		
		ConfigurableApplicationContext context = SpringApplication.run(FinanceTrackerApplication.class, args);
		
		// You can also get the active profile programmatically
		String[] activeProfiles = context.getEnvironment().getActiveProfiles();
		System.out.println("Active profiles: " + String.join(", ", activeProfiles));
	}

}
