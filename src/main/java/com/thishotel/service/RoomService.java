package com.thishotel.service;

import com.thishotel.dto.request.CreateRoomRequestDTO;
import com.thishotel.exception.InvalidInputException;
import com.thishotel.exception.RoomNotFoundException;
import com.thishotel.mapper.RoomMapper;
import com.thishotel.model.Room;
import com.thishotel.repository.RoomRepository;
import com.thishotel.validation.RoomValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomValidationService roomValidationService;
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Transactional
    public Room createRoom(CreateRoomRequestDTO dto) {
        roomValidationService.validateRoomCreation(dto);

        if (roomRepository.existsByRoomNumber(dto.getRoomNumber())) {
            throw new InvalidInputException("Room number: '" + dto.getRoomNumber() + "' already exists.", 2001);
        }

        Room room = roomMapper.toRoom(dto);
        return roomRepository.save(room);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id, 2006));
    }

}
