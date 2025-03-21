package com.thishotel.service;

import com.thishotel.dto.request.CreateRoomRequestDTO;
import com.thishotel.dto.request.RoomStatusDTO;
import com.thishotel.dto.request.UpdateRoomRequestDTO;
import com.thishotel.dto.response.RoomResponseDTO;
import com.thishotel.enums.RoomStatus;
import com.thishotel.exception.InvalidInputException;
import com.thishotel.exception.RoomNotFoundException;
import com.thishotel.mapper.RoomMapper;
import com.thishotel.model.Room;
import com.thishotel.repository.RoomRepository;
import com.thishotel.security.JwtUtil;
import com.thishotel.util.ServiceUtil;
import com.thishotel.validation.RoomValidationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomValidationService roomValidationService;
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final ServiceUtil serviceUtil;
    private final JwtUtil jwtUtil;

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

    @Transactional
    public Room updateRoom(Long id, UpdateRoomRequestDTO dto) {
        Room roomToUpdate = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id, 2006));

        roomValidationService.validateUpdateRoomNumber(roomToUpdate, dto, roomRepository.existsByRoomNumber(dto.getRoomNumber()));
        roomValidationService.validateRoomTypeAndBeds(dto, roomToUpdate);
        roomValidationService.validatePrice(dto.getPrice());
        roomValidationService.validateFloor(dto.getFloor());

        serviceUtil.updateIfNotNull(dto.getRoomNumber(), roomToUpdate::setRoomNumber);
        serviceUtil.updateIfNotNull(dto.getPrice(), roomToUpdate::setPrice);
        serviceUtil.updateIfNotNull(dto.getFloor(), roomToUpdate::setFloor);
        serviceUtil.updateIfNotNull(dto.getRoomType(), roomToUpdate::setRoomType);
        serviceUtil.updateIfNotNull(dto.getBedTypes(), roomToUpdate::setBedTypes);
        serviceUtil.updateIfNotNull(dto.getIsDisabledFriendly(), roomToUpdate::setIsDisabledFriendly);
        serviceUtil.updateIfNotNull(dto.getIsSuite(), roomToUpdate::setIsSuite);
        serviceUtil.updateIfNotNull(dto.getRoomStatus(), roomToUpdate::setRoomStatus);
        serviceUtil.updateIfNotNull(dto.getRoomView(), roomToUpdate::setRoomView);
        serviceUtil.updateIfNotNull(dto.getHasBalcony(), roomToUpdate::setHasBalcony);
        serviceUtil.updateIfNotNull(dto.getHasTerrace(), roomToUpdate::setHasTerrace);

        return roomRepository.save(roomToUpdate);
    }

    @Transactional
    public String updateCleanerRoomStatus(Long id, RoomStatusDTO dto, Long cleanerId) {
        Room roomToUpdate = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id, 2006));

        roomValidationService.validateRoomCleanerOwner(roomToUpdate, cleanerId);
        roomValidationService.validateOnlyOneCleaning(dto, roomRepository.existsByCleanerIdAndRoomStatus(cleanerId, RoomStatus.CLEANING));
        roomValidationService.validateRoomStatusTransition(roomToUpdate.getRoomStatus(), dto.getRoomStatus());

        roomToUpdate.setRoomStatus(dto.getRoomStatus());
        if (dto.getRoomStatus() == RoomStatus.CLEANING) {
            roomToUpdate.setCleanerId(cleanerId);
        } else if (dto.getRoomStatus() == RoomStatus.TO_CLEAN || dto.getRoomStatus() == RoomStatus.AVAILABLE) {
            roomToUpdate.setCleanerId(null);
        }

        roomRepository.save(roomToUpdate);

        return roomToUpdate.getRoomNumber();
    }

    public Long getCleanerIdFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return jwtUtil.extractId(token);
        }
        throw new IllegalStateException("No valid JWT token  found in request");
    }

    public List<RoomResponseDTO> getCleanerWork(Long cleanerId) {
        return roomRepository.findAllByRoomStatusInAndCleanerIdIsNullOrCleanerId(
                List.of(RoomStatus.TO_CLEAN, RoomStatus.CLEANING), cleanerId)
                        .stream()
                        .map(roomMapper::toRoomResponseDTO)
                        .toList();
    }
}
