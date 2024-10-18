package syksy2024.backend.model;
import java.time.LocalDate;

import jakarta.validation.constraints.*;

public class SignUpForm {

    @NotEmpty
    @Size(min=3, max=100)
    private String username = "";

    @NotEmpty
    @Size(min=3, max=100)
    private String password = "";

    @NotEmpty
    @Size(min=3, max=100)
    private String passwordCheck = "";

    @NotEmpty
    private String role = "USER";

    @NotEmpty
    @Size(min = 1, max = 100)
    private String firstName = "";

    @NotEmpty
    @Size(min = 1, max = 100)
    private String lastName = "";

    @NotNull(message = "Date of birth is required")
    @PastOrPresent(message = "Date of birth cannot be in the future")
    private LocalDate dateOfBirth;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    

}
