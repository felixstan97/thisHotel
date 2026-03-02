package com.thishotel.model;

import com.thishotel.enums.Shift;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@DiscriminatorValue("CLEANER")
@Getter
@Setter
@NoArgsConstructor
public class Cleaner extends User {

    @NotNull
    @Enumerated(EnumType.STRING)
    private Shift shift = Shift.TO_BE_ASSIGNED;

    private String cleaningArea;
}