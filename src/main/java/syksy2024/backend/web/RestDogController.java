package syksy2024.backend.web;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import syksy2024.backend.model.Dog;
import syksy2024.backend.repository.DogRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
public class RestDogController {

    private final DogRepository dRepo;

    public RestDogController(DogRepository dRepo) {
        this.dRepo = dRepo;
    }

    @GetMapping("/dogs")
    public Iterable<Dog> getAllDogs() {
        return dRepo.findAll();
    }

    @GetMapping("/dogs/{id}")
    public ResponseEntity<Dog> getDogById(@PathVariable("id") Long id) {
        return dRepo.findById(id)
        .map(dog -> ResponseEntity.ok(dog))
        .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/dogs")
    Dog newDog(@RequestBody Dog newDog) {
        return dRepo.save(newDog);
    }
    
    @PutMapping("dogs/{id}")
    public ResponseEntity<Dog> editDog(@PathVariable Long id, @RequestBody Dog editedDog) {
        if (!dRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        editedDog.setId(id);
        return ResponseEntity.ok(dRepo.save(editedDog));
    }



}
