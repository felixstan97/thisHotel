package com.thishotel.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequestEmailDTO {
    
    @NotBlank(message = "Email is required.")
    private String email;

}
