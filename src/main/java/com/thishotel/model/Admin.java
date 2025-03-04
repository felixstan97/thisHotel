package com.thishotel.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

//    TODO : da capire come implementare e cosa -> puo creare le utenze, gestire le utenze, creare utenze, ruoli etc. (receptionist, cleaner, etc)


}
