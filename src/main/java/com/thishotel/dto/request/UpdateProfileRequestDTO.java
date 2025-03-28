package com.thishotel.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateProfileRequestDTO {
    @Size(min = 2, max = 100, message = "Must be between 2 and 100 characters")
    private String firstName;

    @Size(min = 2, max = 100, message = "Must be between 2 and 100 characters")
    private String lastName;

    @Email(message = "The E-Mail is not valid")
    private String email;

    @Past(message = "Date of birth must be a past date")
    private LocalDate dateOfBirth;

    private String phoneNumber;
}
