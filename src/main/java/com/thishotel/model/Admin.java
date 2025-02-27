package com.thishotel.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

//    TODO : da capire come implementare e cosa -> puo creare le utenze (receptionist, cleaner, etc)
//    le cose che puo fare lui non sono descrivibili qui, ma nel service penso

}
