package com.thishotel.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("CLEANER")
public class Cleaner extends User {


//    VARIABLES
//    todo -> da capire se ha senso metterlo anche agli altri utenti un "badge" o un codice identificativo
    private String individualBadgeCode;

    private String cleaningArea;

    @OneToMany(mappedBy = "handledBy")
    private List<UrgencyRequest> handledUrgencies;

    @Column(nullable = false)
    private boolean isActive = true;


//    GETTER SETTER
    public String getCleaningArea() {
        return cleaningArea;
    }

    public void setCleaningArea(String cleaningArea) {
        this.cleaningArea = cleaningArea;
    }

    public List<UrgencyRequest> getHandledUrgencies() {
        return handledUrgencies;
    }

    public void setHandledUrgencies(List<UrgencyRequest> handledUrgencies) {
        this.handledUrgencies = handledUrgencies;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
