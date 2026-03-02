package com.thishotel.dto.response;

import com.thishotel.enums.BedType;
import com.thishotel.enums.RoomStatus;
import com.thishotel.enums.RoomType;
import com.thishotel.enums.RoomView;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class RoomDetailResponseDTO {
    private Long id;
    private String roomNumber;
    private RoomType roomType;
    private List<BedType> bedTypes;
    private BigDecimal price;
    private Boolean isDisabledFriendly;
    private int floor;
    private Boolean isSuite;
    private RoomStatus roomStatus;
    private RoomView roomView;
    private Boolean hasBalcony;
    private Boolean hasTerrace;
}
