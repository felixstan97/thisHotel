package com.thishotel.dto.request;

import com.thishotel.enums.BedType;
import com.thishotel.enums.RoomType;
import com.thishotel.enums.RoomView;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateRoomRequestDTO {
    @NotBlank(message = "Room number is required")
    private String roomNumber;

    @NotNull(message = "Room type is required")
    private RoomType roomType;

    @NotNull(message = "Bed types are required")
    @Size(min = 1, message = "At least one bed type is required")
    private List<BedType> bedTypes;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    private Boolean isDisabledFriendly;

    @NotNull(message = "Floor is required")
    @Min(value = 0, message = "Floor cannot be negative")
    private Integer floor;

    private Boolean isSuite;

    private RoomView roomView;

    private Boolean hasBalcony;

    private Boolean hasTerrace;

}
