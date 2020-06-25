package jp.co.ssd.bi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {
//		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
//})
public class BiFileUploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiFileUploadApplication.class, args);
	}
}

