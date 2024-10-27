package syksy2024.backend.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;

import syksy2024.backend.model.Dog;


public interface DogRepository extends CrudRepository<Dog, Long>, PagingAndSortingRepository<Dog, Long> {
    List<Dog> findAllByOrderByNameAsc();
    List<Dog> findAllByOrderByDateOfBirthAsc();
    List<Dog> findByBreedId(Long breedId);
    List<Dog> findByOwnerId(Long ownerId);
    Page<Dog> findByBreed_Name(String breed, Pageable pageable);
    Page<Dog> findByOwner_FirstNameContainingOrOwner_LastNameContaining(String firstName, String lastName, Pageable pageable);
}
