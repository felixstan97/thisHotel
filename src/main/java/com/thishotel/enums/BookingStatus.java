package com.thishotel.enums;

public enum BookingStatus {
    PENDING, // il cliente da solo fa la prenotazione registrandosi, ma senza pagare il soggiorno
    CONFIRMED, // il cliente che si prenota da solo, paga anche per il soggiorno selezionato. il receptionist che fa la prenotazione allo sportello,
    CHECKED_IN, // quando effettivamente il cliente entra dentro la stanza
    COMPLETED, // il soggiorno del cliente e finito
    CANCELLED, //il cliente ha cancellato la prenotazione
    NO_SHOW // il cliente non si e presentato
}
