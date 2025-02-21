package dev.hamzaApi.movies;

import org.springframework.boot.SpringApplication; // contains a method called run
import org.springframework.boot.autoconfigure.SpringBootApplication; // spring boot annotation (lets java know what the class does)
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class MoviesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviesApplication.class, args);
	}
	
}

