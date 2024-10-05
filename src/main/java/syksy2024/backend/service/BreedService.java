package syksy2024.backend.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import syksy2024.backend.model.Breed;

@Service
public class BreedService {

    @Value("${api.dog.key}")
    private String apiKey;

    @Autowired
    private RestTemplate template = new RestTemplate();

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
            throw new RuntimeException("API kutsu epäonnistui: " + response.getStatusCode());
        }
    }
    
}
