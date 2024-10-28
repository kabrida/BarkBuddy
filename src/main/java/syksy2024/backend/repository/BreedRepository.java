package syksy2024.backend.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import syksy2024.backend.model.Breed;


public interface BreedRepository extends CrudRepository <Breed, Long> {
    boolean existsById(Long id);
    Optional <Breed> findByName(String name);

}
