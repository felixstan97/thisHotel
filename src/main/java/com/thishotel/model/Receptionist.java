package com.thishotel.model;

import com.thishotel.enums.Shift;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("RECEPTIONIST")
@Getter
@Setter
@NoArgsConstructor
public class Receptionist extends User {


//    VARIABLES

    @NotNull
    @Enumerated(EnumType.STRING)
    private Shift shift;


}
