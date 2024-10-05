package syksy2024.backend.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Dog {
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

 

    private String name;

    private String breed;
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name="owner_id")
    private Owner owner;

    public Dog() {
    }

    public Dog(String name, String breed, LocalDate dateOfBirth, Owner owner) {
        this.name = name;
        this.breed = breed;
        this.dateOfBirth = dateOfBirth;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Dog [id=" + id + ", name=" + name + ", breed=" + breed + ", dateOfBirth=" + dateOfBirth + ", owner="
                + owner + "]";
    }

    


}
