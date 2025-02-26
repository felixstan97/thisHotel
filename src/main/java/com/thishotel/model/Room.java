package com.thishotel.model;

import com.thishotel.enums.BedType;
import com.thishotel.enums.RoomStatus;
import com.thishotel.enums.RoomType;
import com.thishotel.enums.RoomView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {


//  VARIABILI

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoomType roomType;  // Enum per il tipo di stanza (SINGLE, DOUBLE, TRIPLE, ecc.)

    @NotNull
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = BedType.class)
    @CollectionTable(name = "room_beds", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "bed_type")
    private List<BedType> bedTypes; // (ad esempio, [SINGLE] per una singola, [MATRIMONIAL, SINGLE] per una tripla)

    @NotNull
    @Positive
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    private Boolean isDisabledFriendly = false; // Accessibilità per disabili

    @NotNull
    @Min(0)
    private int floor;

    private Boolean isSuite = false;

    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus; // Stato della stanza (AVAILABLE, OCCUPIED, CLEANING, UNDER_MAINTENANCE, TO_CLEAN)

    @Enumerated(EnumType.STRING)
    private RoomView roomView; // Tipo di vista (SEA, MOUNTAIN, LAKE, CITY, GARDEN, NO_VIEW)

    private Boolean hasBalcony = false;

    private Boolean hasTerrace = false;


    // Getter e Setter

    public Long getId() {
        return id;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public List<BedType> getBedTypes() {
        return bedTypes;
    }

    public void setBedTypes(List<BedType> bedTypes) {
        this.bedTypes = bedTypes;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getIsDisabledFriendly() {
        return isDisabledFriendly;
    }

    public void setIsDisabledFriendly(Boolean isDisabledFriendly) {
        this.isDisabledFriendly = isDisabledFriendly;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public Boolean getIsSuite() {
        return isSuite;
    }

    public void setIsSuite(Boolean isSuite) {
        this.isSuite = isSuite;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public RoomView getRoomView() {
        return roomView;
    }

    public void setRoomView(RoomView roomView) {
        this.roomView = roomView;
    }
    public Boolean getHasBalcony() {
        return hasBalcony;
    }

    public void setHasBalcony(Boolean hasBalcony) {
        this.hasBalcony = hasBalcony;
    }

    public Boolean getHasTerrace() {
        return hasTerrace;
    }

    public void setHasTerrace(Boolean hasTerrace) {
        this.hasTerrace = hasTerrace;
    }

    // Metodo per ottenere il numero massimo di persone consentite per la stanza
    public int calculateMaxOccupancy() {
        return roomType.getMaxOccupancy();
    }

//    metodo da implementare nel service ma segnato qui per tenere traccia
//    isBookedDuring() -> per capire se ci sono gia prenotazioni per quella determinata stanza in un determinato periodo

}