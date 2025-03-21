package com.thishotel.model;

import com.thishotel.enums.BedType;
import com.thishotel.enums.RoomStatus;
import com.thishotel.enums.RoomType;
import com.thishotel.enums.RoomView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    @Column(unique = true)
    private String roomNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoomType roomType;  // (SINGLE, DOUBLE, TRIPLE, ecc.)

    @NotNull
    @Enumerated(EnumType.STRING)
    @Size(min = 1, message = "At least one bed type is required")
    @ElementCollection(targetClass = BedType.class)
    @CollectionTable(name = "room_beds", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "bed_type")
    private List<BedType> bedTypes; // (ad esempio, [SINGLE] per una singola, [MATRIMONIAL, SINGLE] per una tripla)

    @NotNull
    @Positive
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    private Boolean isDisabledFriendly = false;

    @NotNull
    @Min(0)
    private int floor;

    private Boolean isSuite = false;

    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus = RoomStatus.AVAILABLE;  // (AVAILABLE, OCCUPIED, CLEANING, UNDER_MAINTENANCE, TO_CLEAN)

    @Enumerated(EnumType.STRING)
    private RoomView roomView = RoomView.NO_VIEW; // (SEA, MOUNTAIN, LAKE, CITY, GARDEN, NO_VIEW)

    private Boolean hasBalcony = false;

    private Boolean hasTerrace = false;

    private Long cleanerId;

    public int calculateMaxOccupancy() {
        return roomType.getMaxOccupancy();
    }


}
