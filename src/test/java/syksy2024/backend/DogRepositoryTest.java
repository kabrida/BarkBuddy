package syksy2024.backend;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import syksy2024.backend.model.Breed;
import syksy2024.backend.model.Dog;
import syksy2024.backend.model.Owner;
import syksy2024.backend.repository.BreedRepository;
import syksy2024.backend.repository.DogRepository;
import syksy2024.backend.repository.OwnerRepository;

@SpringBootTest(classes = BackendApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DogRepositoryTest {

    @Autowired
    private DogRepository dRepo;

    @Autowired
    private BreedRepository bRepo;

    @Autowired
    private OwnerRepository oRepo;

    @Test
    public void testNewDog() {
        Optional<Breed> breedOptional = bRepo.findById(1L);
        Optional<Owner> ownerOptional = oRepo.findById(1L);
        // Create a new dog
        if (breedOptional.isPresent() && ownerOptional.isPresent()) {
        Dog dog = new Dog("Buddy", breedOptional.get(), "NO12345", LocalDate.now(), ownerOptional.get());
        dRepo.save(dog);
        assertThat(dog.getId()).isNotNull();
        } 
    }

    @Test
    public void testDeleteDog() {
        Optional<Dog> dogOptional = dRepo.findById(1L);
        if (dogOptional.isPresent()) {
            dRepo.delete(dogOptional.get());
            assertThat(dRepo.findById(1L)).isEmpty();
        }
    }

    @Test
    public void testUpdateDog() {
        Optional<Dog> dogOptional = dRepo.findById(1L);
        Optional<Breed> breedOptional = bRepo.findById(1L);
        Optional<Owner> ownerOptional = oRepo.findById(1L);
        if (dogOptional.isPresent() && breedOptional.isPresent() && ownerOptional.isPresent()) {
            Dog dog = dogOptional.get();
            dog.setName("Buddy Updated");
            dog.setBreed(breedOptional.get());
            dog.setRegNum("NO12346");
            dog.setDateOfBirth(LocalDate.now());
            dog.setOwner(ownerOptional.get());
            dRepo.save(dog);
            assertThat(dog.getName()).isEqualTo("Buddy Updated");
        }
    }


}
