package syksy2024.backend;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import syksy2024.backend.model.Breed;
import syksy2024.backend.model.Dog;
import syksy2024.backend.repository.DogRepository;
import syksy2024.backend.service.BreedService;

@SpringBootApplication
public class BackendApplication {
	private static final Logger log = LoggerFactory.getLogger(BackendApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner initData(DogRepository dogRepository, BreedService breedService) {
		return args -> {

		Breed[] breeds = breedService.findAllBreeds();

		if (breeds != null && breeds.length > 0) {
		
		Dog d1 = new Dog("Possu", breeds[0].getName(), LocalDate.of(2016, 1, 13), null);
		Dog d2 = new Dog("Riesu", breeds[1].getName(), LocalDate.of(2022, 1, 1), null);
		
		dogRepository.save(d1);
		dogRepository.save(d2);

		log.info("Created some dogs");
		log.info(d1.toString());
            log.info(d2.toString());
		} else {
            log.warn("No breeds found");
        }

		};
	}


}
