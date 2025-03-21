package com.thishotel.validation;

import com.thishotel.dto.request.*;
import com.thishotel.enums.BedType;
import com.thishotel.enums.RoomStatus;
import com.thishotel.enums.RoomType;
import com.thishotel.exception.InvalidInputException;
import com.thishotel.exception.InvalidTokenException;
import com.thishotel.model.PasswordResetToken;
import com.thishotel.model.Room;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Service
public class RoomValidationService {

    public void validateRoomCreation(CreateRoomRequestDTO dto) {
        if (dto == null)
            throw new InvalidInputException("Room data cannot be null.", 2000);

        validateRoomNumberNotEmpty(dto.getRoomNumber());
        validateBedTypes(dto.getBedTypes(), dto.getRoomType());
    }

    public void validateUpdateRoomNumber(Room room, UpdateRoomRequestDTO dto, boolean alreadyExist){
        if (dto.getRoomNumber() != null){
            validateRoomNumberNotEmpty(dto.getRoomNumber());
            if (!dto.getRoomNumber().equals(room.getRoomNumber())
                && alreadyExist) {
                throw new InvalidInputException("Room number: '" + dto.getRoomNumber() + "' already exists.", 2001);
            }
        }
    }

    public void validateRoomNumberNotEmpty(String roomNumber) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new InvalidInputException("Room number: '" + roomNumber +"' cannot be empty.", 2002);
        }
    }

    public void validateBedTypes(List<BedType> bedTypes, RoomType roomType) {
        if (bedTypes == null || bedTypes.isEmpty()) {
            throw new InvalidInputException("At least one bed type is required.", 2003);
        }

        int maxOccupancy = roomType.getMaxOccupancy();
        int bedCapacity = bedTypes.size();
        if (bedCapacity > maxOccupancy) {
            throw new InvalidInputException("Too many beds for " + roomType + " room.", 2004);
        }

        if (roomType == RoomType.SINGLE && bedCapacity > 1) {
            throw new InvalidInputException("Single room cannot have more than one bed.", 2005);
        }
    }

    public void validateRoomTypeAndBeds(UpdateRoomRequestDTO dto, Room room) {
        if (dto.getRoomType() != null || dto.getBedTypes() != null) {
            RoomType roomType = dto.getRoomType() != null ? dto.getRoomType() : room.getRoomType();
            List<BedType> bedTypes = dto.getBedTypes() != null ? dto.getBedTypes() : room.getBedTypes();
            validateBedTypes(bedTypes,roomType);
        }
    }

    public void validatePrice(BigDecimal price) {
        if (price != null){
            if (price.compareTo(BigDecimal.ZERO) <= 0){
                throw new InvalidInputException("Price must be positive.", 2007);
            }
        }
    }

    public void validateFloor(Integer floor) {
        if (floor != null && floor < 0) {
            throw new InvalidInputException("Floor cannot be negative.", 2008);
        }
    }


//    todo - da capire se serve ancora  questo o e sostituito da quello sotto
    public void validateRoomStatus(RoomStatus currentStauts, RoomStatus newStatus) {
        if (currentStauts == RoomStatus.TO_CLEAN && newStatus == RoomStatus.CLEANING){
            return;
        }
        if (currentStauts == RoomStatus.CLEANING && newStatus == RoomStatus.AVAILABLE){
            return;
        }
        throw new InvalidInputException("Invalid status transition from: '" + currentStauts + "' to: '" + newStatus + "'.", 2009);
    }

    public void validateRoomStatusTransition(RoomStatus currentStauts, RoomStatus newStatus) {
        if (currentStauts == RoomStatus.TO_CLEAN && newStatus == RoomStatus.CLEANING){
            return;
        }
        if (currentStauts == RoomStatus.CLEANING && newStatus == RoomStatus.AVAILABLE) {
            return;
        }
        if (currentStauts == RoomStatus.CLEANING && newStatus == RoomStatus.TO_CLEAN) {
            return;
        }
        throw new InvalidInputException("Invalid status transition from: '" + currentStauts + "' to: '" + newStatus + "'.", 2009);
    }

    public void validateOnlyOneCleaning(RoomStatusDTO dto, boolean existingAlready) {
        if (dto.getRoomStatus() == RoomStatus.CLEANING && existingAlready) {
            throw new InvalidInputException("You already have a room in CLEANING. Finish o cancel it first.", 2011);
        }
    }

    public void validateRoomCleanerOwner(Room room, Long cleanerId){
        if (room.getRoomStatus() == RoomStatus.CLEANING && !Objects.equals(room.getCleanerId(), cleanerId)) {
            throw new InvalidInputException("You can only update your onw CLEANING rooms", 2012);
        }
    }


}
