package com.thishotel.dto.request;

import com.thishotel.enums.RoomStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoomStatusDTO {
    @NotNull(message = "Room status is required")
    private RoomStatus roomStatus;
}
