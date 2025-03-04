package com.thishotel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {


//    VARIABLES

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;


//    GETTER SETTER

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
