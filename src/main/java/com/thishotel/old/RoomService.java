package com.thishotel.old;

import com.thishotel.model.Room;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

//@Entity
public class RoomService {

//     TODO: da aggiungere in futuro migliori implemetnazioni come la notifica di pulizia urgente
//todo    l utente dalla sua stanza, effettua una richiesta tramite il suo conto, arriva una notifica
//todo    ad un dipendente o un centro di gestione(?) o alla reception che poi gestisce la cosa, ma come (?)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    private String description;  // Descrizione del servizio richiesto

    private boolean isCompleted;

    @CreationTimestamp
    private LocalDateTime requestDate;

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }
}
