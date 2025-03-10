package com.thishotel.model;

import com.thishotel.enums.Shift;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("MANAGER")
@Getter
@Setter
@NoArgsConstructor
public class Manager extends User{

    @NotNull
    @Enumerated(EnumType.STRING)
    private Shift shift = Shift.TO_BE_ASSIGNED;

}
