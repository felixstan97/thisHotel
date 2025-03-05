package com.thishotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)                                       // Usando una sola tabella per tutte le entità
@DiscriminatorColumn(name = "user_table", discriminatorType = DiscriminatorType.STRING)     // Colonna per distinguere i tipi
@Table(name = "users")
@Getter
@Setter
public abstract class User {


//    VARIABLES

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull(message = "The firstName is required")
    @Size(min = 2, max = 100, message = "Must be between 2 and 100 characters")
    @Column(nullable = false)
    private String firstName;

    @NotBlank
    @NotNull(message = "The lastName is required")
    @Size(min = 2, max = 100, message = "Must be between 2 and 100 characters")
    @Column(nullable = false)
    private String lastName;

    @NotBlank
    @NotNull(message = "Phone number is required")
    @Column(nullable = false)
    private String phoneNumber;

    @Past(message = "Date of birth must be a past date")
    private LocalDate dateOfBirth;

    @NotBlank
    @Column(unique = true, nullable = false)
    @NotNull(message = "The E-Mail is required")
    @Email(message = "The E-Mail is not valid")
    private String email;

    @NotBlank
    @NotNull(message = "The password is required")
    @Size(min = 6, max = 250, message = "The password must have at least 6 characters")
    @Column(nullable = false)
    @JsonIgnore
    @Setter
    private String password;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(insertable = false)
    private LocalDateTime deletedAt;

    @Column(nullable = false)
    private boolean isActive = true;

//    GETTER SETTER

//    public Long getId() {
//        return id;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public LocalDate getDateOfBirth() {
//        return dateOfBirth;
//    }
//
//    public void setDateOfBirth(LocalDate dateOfBirth) {
//        this.dateOfBirth = dateOfBirth;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public LocalDateTime getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(LocalDateTime updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public LocalDateTime getDeletedAt() {
//        return deletedAt;
//    }
//
//    public void setDeletedAt(LocalDateTime deletedAt) {
//        this.deletedAt = deletedAt;
//    }
//
//    public boolean isActive() {
//        return isActive;
//    }
//
//    public void setActive(boolean active) {
//        isActive = active;
//    }

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
