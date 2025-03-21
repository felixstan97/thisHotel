package com.thishotel.controller;

import com.thishotel.dto.request.RoomStatusDTO;
import com.thishotel.dto.response.ApiResponseDTO;
import com.thishotel.dto.response.RoomResponseDTO;
import com.thishotel.service.RoomService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cleaning-tasks")
@RequiredArgsConstructor
public class CleanerController {

    private final RoomService roomService;

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponseDTO> setCleaningStatusRoom(@PathVariable Long id,
                                                                @Valid @RequestBody RoomStatusDTO roomStatusDTO,
                                                                HttpServletRequest request) {
        Long cleanerId = roomService.getCleanerIdFromRequest(request);
        String roomNumber = roomService.updateCleanerRoomStatus(id,roomStatusDTO, cleanerId);
        String message = "Room with number: '" + roomNumber + "' now are updated with status: '" + roomStatusDTO.getRoomStatus() + "'.";
        ApiResponseDTO responseDTO = new ApiResponseDTO(message);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/my-work")
    public ResponseEntity<ApiResponseDTO> getMyWork(HttpServletRequest request) {
        Long cleanerId = roomService.getCleanerIdFromRequest(request);
        List<RoomResponseDTO> responseDTOS = roomService.getCleanerWork(cleanerId);
        String message = responseDTOS.isEmpty() ? "No rooms assigned or to clean found." : "Your work retrieved successfully";
        ApiResponseDTO responseDTO = new ApiResponseDTO(responseDTOS, message);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

}
