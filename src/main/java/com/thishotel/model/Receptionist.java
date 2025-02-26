package com.thishotel.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("RECEPTIONIST")
public class Receptionist extends User {

    private String shift;       // morning - afternoon - night


    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
