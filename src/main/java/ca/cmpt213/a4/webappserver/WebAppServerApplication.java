package ca.cmpt213.a4.webappserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class of the web server
 */
@SpringBootApplication
public class WebAppServerApplication {

	/**
	 * Main function of the web server
	 * @param args default argument given by Java
	 */
	public static void main(String[] args) {
		SpringApplication.run(WebAppServerApplication.class, args);
	}

}
