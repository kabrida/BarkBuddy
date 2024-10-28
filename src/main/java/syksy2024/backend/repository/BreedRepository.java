package syksy2024.backend.repository;

import org.springframework.data.repository.CrudRepository;

import syksy2024.backend.model.Breed;


public interface BreedRepository extends CrudRepository <Breed, Long> {
    boolean existsById(Long id);
    Breed findByName(String name);

}
