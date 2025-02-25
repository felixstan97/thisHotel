package com.thishotel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "bookings")
public class Booking {


//  VARIABILI

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @NotNull
    @FutureOrPresent(message = "The start date must be in the present or future")
    private LocalDate checkInDate;

    @NotNull
    @Future(message = "The end date must be in the future")
    private LocalDate checkOutDate;

    private LocalDate cancelledAt;

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(length = 700)
    private String notes;


//  GETTER E SETTER

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public LocalDate getCancelledAt() {
        return cancelledAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


//  OTHERS METHODS

    // assicurarsi che nella fase di creazione il valore isActive, sia true
    @PrePersist
    protected void onCreate(){
        this.isActive = true;
    }

    public void cancelBooking(){
        if(!this.isActive){
            throw new IllegalArgumentException("The booking is already cancelled");
        }
        this.isActive = false;
        this.cancelledAt = LocalDate.now();
    }

    // return true if booking status is active (not cancelled)
    public boolean isActive(){
        return this.isActive;
    }

    // make a date validation
    public boolean isValid(){
        return isActive && checkInDate != null && checkOutDate != null && checkOutDate.isAfter(checkInDate);
    }

    public long getBookingDuration(){
        if(checkInDate != null && checkOutDate != null){
            if (checkOutDate.isBefore(checkInDate)){
                throw new IllegalArgumentException("Check-out date must be after check-in date");
            }
            return ChronoUnit.DAYS.between(checkInDate,checkOutDate);
        }
        return 0;
    }

//--- da capire utilizzo in futuro
    // Metodo per verificare se una stanza è disponibile in un periodo specifico (da implementare nel service layer)
    public boolean isRoomAvailable(Room room, LocalDate startDate, LocalDate endDate) {
        // Logica per verificare la disponibilità della stanza. Si può implementare nel service layer.
        return true;  // Solo un segnaposto per ora.
    }


}