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
import syksy2024.backend.repository.BreedRepository;
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
	public CommandLineRunner initData(DogRepository dogRepository, BreedRepository breedRepository) {
		return args -> {
			Long breedId = 12L;
			Breed breed = breedRepository.findById(breedId).orElse(null);
			Dog dog = new Dog("Possu", breed, LocalDate.of(2016, 1, 13), null);
			log.info("Saved dog: " + dog.toString());
			dogRepository.save(dog);

		};
	}


}
