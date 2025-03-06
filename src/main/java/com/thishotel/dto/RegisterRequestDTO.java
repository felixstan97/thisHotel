package com.thishotel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
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

}
