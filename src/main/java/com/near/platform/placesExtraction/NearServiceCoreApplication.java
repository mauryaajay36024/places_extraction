package com.near.platform.placesExtraction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.near.platform.placesExtraction", "mcm", "utility", "mongodb"})
public class NearServiceCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(NearServiceCoreApplication.class, args);
	}
}