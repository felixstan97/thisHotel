package com.thishotel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)                                       // Usando una sola tabella per tutte le entità
@DiscriminatorColumn(name = "user_table", discriminatorType = DiscriminatorType.STRING)     // Colonna per distinguere i tipi
@Table(name = "users")
public abstract class User {


//    VARIABLES

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull(message = "The username is mandatory")
    @Size(min = 2, max = 50, message = "Must be between 2 and 50 characters")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    @NotNull(message = "The password is mandatory")
    @Size(min = 6, max = 250, message = "The password must have at least 6 characters")
    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    @NotNull(message = "The E-Mail is mandatory")
    @Email(message = "The E-Mail is not valid")
    private String email;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(insertable = false)
    private LocalDateTime deletedAt;


//    GETTER SETTER

    public Long getId() {
        return id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDeletedAt(){
        return deletedAt;
    }


//    DELETE SECTION

    public boolean isDeleted(){
        return deletedAt != null;
    }

    public void softUserDelete(){
        if(this.deletedAt == null){
            this.deletedAt = LocalDateTime.now();
        }
    }

    public void restoreDeletedUser(){
        if(this.deletedAt != null){
            this.deletedAt = null;
        }
    }

}
