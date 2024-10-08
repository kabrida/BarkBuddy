package syksy2024.backend.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import syksy2024.backend.model.Breed;
import syksy2024.backend.repository.BreedRepository;

@Service
public class BreedService {

    @Value("${api.dog.key}")
    private String apiKey;

    @Autowired
    private RestTemplate template = new RestTemplate();

    @Autowired
    private BreedRepository breedRepository;

    public Breed[] findAllBreeds() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Breed[]> response = template.exchange(
            "https://api.thedogapi.com/v1/breeds",
            HttpMethod.GET,
            entity,
            Breed[].class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("API call failed: " + response.getStatusCode());
        }
    }

    @PostConstruct
    public void init() {
        Breed[] breedsFromApi = findAllBreeds();
        for (Breed breed : breedsFromApi) {
            breedRepository.save(breed);
        }
    }
    
}
