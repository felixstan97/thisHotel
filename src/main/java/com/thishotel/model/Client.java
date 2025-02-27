package com.thishotel.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CLIENT")
public class Client extends User{


//    VARIABLES

    private String loyaltyCardCode;

    @Column(nullable = false)
    private boolean isActive = true;


//    GETTER SETTER

    public String getLoyaltyCardCode() {
        return loyaltyCardCode;
    }

    public void setLoyaltyCardCode(String loyaltyCardCode) {
        this.loyaltyCardCode = loyaltyCardCode;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
