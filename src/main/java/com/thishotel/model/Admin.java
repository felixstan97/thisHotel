package com.thishotel.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("ADMIN")
@Getter
@Setter
@NoArgsConstructor
public class Admin extends User {

//    TODO : da capire come implementare e cosa -> puo creare le utenze, gestire le utenze, creare utenze, ruoli etc. (receptionist, cleaner, etc)


}
