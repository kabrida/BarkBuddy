package syksy2024.backend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import syksy2024.backend.model.Breed;
import syksy2024.backend.service.BreedService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/breeds")
public class BreedRestController {

    @Autowired
    private BreedService breedService;

    @GetMapping
    public Breed[] getAllBreeds() {
        return breedService.findAllBreeds();
    }
    
    

}
