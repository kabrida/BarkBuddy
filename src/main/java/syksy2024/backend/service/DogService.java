package syksy2024.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import syksy2024.backend.model.Dog;
import syksy2024.backend.repository.DogRepository;

@Service
public class DogService {

    @Autowired
    private DogRepository dogRepository;

    public List<Dog> findAllDogsSortedByName() {
    return dogRepository.findAllByOrderByNameAsc();
}


public List<Dog> findAllDogsSortedByDateOfBirth() {
    return dogRepository.findAllByOrderByDateOfBirthAsc();
}

public List<Dog> findDogsByBreed(Long breedId) {
    return dogRepository.findByBreedId(breedId);
}

public List<Dog> findDogsByOwner(Long ownerId) {
    return dogRepository.findByOwnerId(ownerId);
}

}
