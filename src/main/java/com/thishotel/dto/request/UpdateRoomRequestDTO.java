package com.thishotel.dto.request;

import com.thishotel.enums.BedType;
import com.thishotel.enums.RoomStatus;
import com.thishotel.enums.RoomType;
import com.thishotel.enums.RoomView;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UpdateRoomRequestDTO {
    private String roomNumber;
    private RoomType roomType;
    private List<BedType> bedTypes;
    private BigDecimal price;
    private Boolean isDisabledFriendly;
    private Integer floor;
    private Boolean isSuite;
    private RoomStatus roomStatus;
    private RoomView roomView;
    private Boolean hasBalcony;
    private Boolean hasTerrace;
}
