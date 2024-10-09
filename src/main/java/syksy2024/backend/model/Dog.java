package syksy2024.backend.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Table(name="dog")
@Entity
public class Dog {
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dog_name", nullable = false)
    @NotNull(message = "Name is required")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 50 characters")
    private String name;

    @NotNull(message = "Breed is required")
    @ManyToOne
    @JoinColumn(name = "breed_id")
    private Breed breed;

    @Column(name="reg_num", nullable = false, unique = true)
    @NotNull(message = "Registration numner is required")
    private String regNum;


    @Column(name="dog_dob", nullable = false)
    @PastOrPresent(message = "Date of birth cannot be in the future")
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name="owner_id")
    private Owner owner;

    public Dog() {
    }

    public Dog(String name, Breed breed, String regNum, LocalDate dateOfBirth, Owner owner) {
        this.name = name;
        this.breed = breed;
        this.regNum = regNum;
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

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    
    public String getRegNum() {
        return regNum;
    }

    public void setRegNum(String regNum) {
        this.regNum = regNum;
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
        return "Dog [id=" + id + ", name=" + name + ", breed=" + breed + ", regNum=" + regNum + ", dateOfBirth="
                + dateOfBirth + ", owner=" + owner + "]";
    }


    


}
