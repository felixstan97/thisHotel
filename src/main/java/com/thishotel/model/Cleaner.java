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

    @OneToMany(mappedBy = "handledBy")
    private List<UrgencyRequest> handledUrgencies;

}


//    todo -> da capire se ha senso metterlo anche agli altri utenti un "badge" o un codice identificativo
//    private String individualBadgeCode;
