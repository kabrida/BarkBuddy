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
import syksy2024.backend.model.Height;
import syksy2024.backend.model.Weight;
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
        System.out.println("Status code: " + response.getStatusCode());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            System.out.println("Error message: " + response.getBody());
            throw new RuntimeException("API call failed: " + response.getStatusCode());
        }
    }

    @PostConstruct
    public void init() {
        Breed[] breedsFromApi = findAllBreeds();
        for (Breed breed : breedsFromApi) {
            // Tarkista, onko breed jo olemassa tietokannassa
            if (breedRepository.findByName(breed.getName()) == null) {
                // Jos breed on jo olemassa, päivitä se
                breedRepository.save(breed); // tämä päivittää olemassa olevan
            } else {
                // Jos breed ei ole olemassa, tallenna se uutena
                breedRepository.save(breed);
            }
        }
    
    }
}