package com.thishotel.model;

import com.thishotel.enums.Shift;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("RECEPTIONIST")
public class Receptionist extends User {


//    VARIABLES

    @NotNull
    @Enumerated(EnumType.STRING)
    private Shift shift;

    @Column(nullable = false)
    private boolean isActive = true;


//  GETTER SETTER
    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
