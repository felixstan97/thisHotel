package com.thishotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
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
