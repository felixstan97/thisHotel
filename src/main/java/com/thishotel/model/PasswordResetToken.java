package com.thishotel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private String email;
    private LocalDateTime expirationDate;

    public PasswordResetToken(String token, String email){
        this.token = token;
        this.email = email;
        this.expirationDate = LocalDateTime.now().plusHours(2);
    }

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expirationDate);
    }

}
