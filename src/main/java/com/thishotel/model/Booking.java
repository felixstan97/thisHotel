package com.thishotel.model;

import com.thishotel.enums.BookingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
public class Booking {


//  VARIABILI

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus = BookingStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @NotNull
    @FutureOrPresent(message = "The start date must be in the present or future")
    private LocalDate checkInDate;

    @NotNull
    @Future(message = "The end date must be in the future")
    private LocalDate checkOutDate;

    private LocalDateTime cancellationDate;

    private boolean isPaid; //used for custom booking

    @Column(length = 700)
    private String notes;


//  OTHERS METHODS

    // Metodo per annullare la prenotazione
    public void cancelBooking() {
        this.bookingStatus = BookingStatus.CANCELLED;
        this.cancellationDate = LocalDateTime.now();
    }

    // make a date validation
    public boolean isValid(){
        return checkInDate != null && checkOutDate != null && checkOutDate.isAfter(checkInDate);
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


//    TODO -> Da rivedere il concetto di USER dato che user e stato modificato, dovrebbe essere "Client" invece di User (?)
//     -> anche il concetto di isActive o comunque uno stato del booking, non ancora, in_progress, complete ( e quindi se ha senso qui mettere anche lo status DELETED (?))
//     -> rifare la logica della cancellazione del booking, il metodo da chiamare per cancellare il booking [manager]
//    todo -> trovare se possibile uniformita tra LOCALDATE e LOCALDATETIME
