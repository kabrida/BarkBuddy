package syksy2024.backend.repository;

import org.springframework.data.repository.CrudRepository;

import syksy2024.backend.model.Dog;


public interface DogRepository extends CrudRepository<Dog, Long> {
}
