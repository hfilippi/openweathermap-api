package ar.com.vampiro.openweathermapapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OpenweathermapApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenweathermapApiApplication.class, args);
	}

}
