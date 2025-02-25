package com.thishotel.model;

import com.thishotel.enums.BedType;
import com.thishotel.enums.RoomStatus;
import com.thishotel.enums.RoomView;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {


//  VARIABILI

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomType;  // (singola, doppia, tripla)

    private int maxOccupancy; // Numero massimo di persone

    private List<BedType> bedTypes; // (ad esempio, [SINGLE] per una singola, [MATRIMONIAL, SINGLE] per una tripla)

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    private Boolean isDisabledFriendly; // Accessibilità per disabili

    private int floor;

    private Boolean isSuite;

    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus; // Stato della stanza (AVAILABLE, OCCUPIED, CLEANING, UNDER_MAINTENANCE, TO_CLEAN)

    @Enumerated(EnumType.STRING)
    private RoomView roomView; // Tipo di vista (SEA, MOUNTAIN, LAKE, CITY, GARDEN, NO_VIEW)

    private Boolean hasBalcony;

    private Boolean hasTerrace;


    // Getter e Setter

    public Long getId() {
        return id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
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

//    metodo da implementare nel service ma segnato qui per tenere traccia
//    isBookedDuring() -> per capire se ci sono gia prenotazioni per quella determinata stanza in un determinato periodo

}