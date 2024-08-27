package pe.edu.vallegrande.vgmscompetencies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableReactiveFeignClients(basePackages = "pe.edu.vallegrande.vgmscompetencies.application.feignclient")
public class VgMsCompetenciesApplication {

	public static void main(String[] args) {
		SpringApplication.run(VgMsCompetenciesApplication.class, args);
	}

}
