package syksy2024.backend.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Table(name="owner")
@Entity
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    @NotNull(message = "Userame is required")
    @Size(min = 3, max = 100, message = "Username must be between 3 and 50 characters")
    private String username;

    @Column(name = "password_hash", nullable = false)
    @NotNull(message = "Password is required")
    @Size(min = 3, max = 100, message = "Password must be between 3 and 50 characters")
    private String passwordHash;

    @Column(name = "first_name", nullable = false)
    @NotNull(message = "First name is required")
    @Size(min = 1, max = 100, message = "First name must be between 1 and 50 characters")
    private String firstName;

    @Column(name= "last_name", nullable = false)
    @NotNull(message = "Last name is required")
    @Size(min = 1, max = 100, message = "Last name must be between 1 and 50 characters")
    private String lastName;

    @Column(name="owner_dob", nullable = false)
    @PastOrPresent(message = "Date of birth cannot be in the future")
    private LocalDate dateOfBirth;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    @JsonIgnore
    private List<Dog> dogs;

    @Column(name = "user_role")
    private String role;

    public Owner() {
    }

    public Owner(String username, String passwordHash, String firstName, String lastName, LocalDate dateOfBirth, String role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (!role.equals("USER") && !role.equals("ADMIN")) {
            throw new IllegalArgumentException("Role must be USER or ADMIN");
        }
        this.role = role;
    }

    @Override
    public String toString() {
        return "Owner [id=" + id + ", username=" + username + ", passwordHash=" + passwordHash + ", firstName="
                + firstName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth  + ", role="
                + role + "]";
    }



    


}
