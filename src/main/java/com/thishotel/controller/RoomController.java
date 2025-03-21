package com.thishotel.controller;

import com.thishotel.dto.request.CreateRoomRequestDTO;
import com.thishotel.dto.request.UpdateRoomRequestDTO;
import com.thishotel.dto.response.ApiResponseDTO;
import com.thishotel.dto.response.RoomDetailResponseDTO;
import com.thishotel.dto.response.RoomResponseDTO;
import com.thishotel.mapper.RoomMapper;
import com.thishotel.model.Room;
import com.thishotel.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("api/admin/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final RoomMapper roomMapper;

    @PostMapping("/create-room")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO<RoomResponseDTO>> createRoom(@Valid @RequestBody CreateRoomRequestDTO createRoomRequestDTO) {
        Room room = roomService.createRoom(createRoomRequestDTO);
        RoomResponseDTO roomDTO = roomMapper.toRoomResponseDTO(room);
        String message = "Room nr: '" + room.getRoomNumber() + "' created successfully";
        ApiResponseDTO<RoomResponseDTO> response = new ApiResponseDTO<>(roomDTO, message);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<RoomResponseDTO>>> getAllRooms() {
        List<Room> roomList = roomService.getAllRooms();
        List<RoomResponseDTO> roomDTOs = roomList.stream().map(roomMapper::toRoomResponseDTO).toList();
        String message = roomDTOs.isEmpty() ? "No rooms found." : "Rooms retrieved successfully";
        ApiResponseDTO<List<RoomResponseDTO>> response = new ApiResponseDTO<>(roomDTOs, message);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<RoomDetailResponseDTO>> getRoomById(@PathVariable Long id) {
        Room room = roomService.getRoomById(id);
        RoomDetailResponseDTO roomResponseDTO = roomMapper.toRoomDetailResponseDTO(room);
        String message = "Room retrieved successfully.";
        ApiResponseDTO<RoomDetailResponseDTO> responseDTO = new ApiResponseDTO<>(roomResponseDTO, message);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO<RoomDetailResponseDTO>> updateRoom(@PathVariable Long id,
                                                                            @Valid @RequestBody UpdateRoomRequestDTO updateRoomRequestDTO) {
        Room updatedRoom = roomService.updateRoom(id, updateRoomRequestDTO);
        String message = "Room nr: '" + updatedRoom.getRoomNumber() + "' updated succesfully";
        RoomDetailResponseDTO roomResponseDTO = roomMapper.toRoomDetailResponseDTO(updatedRoom);
        ApiResponseDTO responseDTO = new ApiResponseDTO(roomResponseDTO, message);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }



}

