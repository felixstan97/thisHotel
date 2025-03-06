package com.thishotel.model;

import jakarta.persistence.*;
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


//    VARIABLES
//    todo -> da capire se ha senso metterlo anche agli altri utenti un "badge" o un codice identificativo
    private String individualBadgeCode;

    private String cleaningArea;

    @OneToMany(mappedBy = "handledBy")
    private List<UrgencyRequest> handledUrgencies;

}
