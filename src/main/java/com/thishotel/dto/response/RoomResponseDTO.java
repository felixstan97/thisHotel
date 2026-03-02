package com.thishotel.dto.response;

import com.thishotel.enums.RoomStatus;
import com.thishotel.enums.RoomType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomResponseDTO {
    private Long id;
    private String roomNumber;
    private BigDecimal price;
    private int floor;
    private RoomStatus roomStatus;
    private RoomType roomType;
}
