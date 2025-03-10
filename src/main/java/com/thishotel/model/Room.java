package com.thishotel.model;

import com.thishotel.enums.BedType;
import com.thishotel.enums.RoomStatus;
import com.thishotel.enums.RoomType;
import com.thishotel.enums.RoomView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
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


    public int calculateMaxOccupancy() {
        return roomType.getMaxOccupancy();
    }

}