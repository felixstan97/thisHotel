package com.thishotel.dto.response;

import lombok.Data;

@Data
public class UserDetailResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String phoneNumber;
    private String dateOfBirth;
    private String createdAt;
    private String updatedAt;
    private String loyalityCardCode;
}
