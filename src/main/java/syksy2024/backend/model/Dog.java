package syksy2024.backend.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String breed; //Tämä tuodaan ulkoisesta API:sta
    private LocalDate dateOfBirth;

    // private Owner owner; //Tälle luodaan oma entity

    

}
