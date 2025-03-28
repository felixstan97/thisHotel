package com.thishotel.dto.request;

import com.thishotel.enums.Shift;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignShiftRequestDTO {
    @NotNull
    private Long id;
    @NotNull
    private Shift shift;
}
