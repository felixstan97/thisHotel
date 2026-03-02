package com.thishotel.dto.response;

import lombok.Data;

@Data
public class RegistrationResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
