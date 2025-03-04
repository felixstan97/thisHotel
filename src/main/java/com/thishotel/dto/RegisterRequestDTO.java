package com.thishotel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class RegisterRequestDTO {


//    VARIABLES

    @NotBlank(message = "The firstName is required")
    private String firstName;

    @NotBlank(message = "The lastName is required")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @Past(message = "Date of birth must be a past date")
    private LocalDate dateOfBirth;

    @NotBlank(message = "The E-Mail is required")
    @Email(message = "Invalid E-Mail format")
    private String email;

    @NotBlank(message = "The password is required")
    @Size(min = 6, max = 250, message = "Password must be at least 6 characters")
    private String password;


//    GETTER SETTER

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
