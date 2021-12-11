package br.com.poupeAi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PoupeAiApplication {

	public static void main(String[] args) {
//		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
//		System.out.println(bc.encode("karina"));
		SpringApplication.run(PoupeAiApplication.class, args);
	}

}
