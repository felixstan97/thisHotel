package com.thishotel.validation;

import com.thishotel.dto.request.CreateRoomRequestDTO;
import com.thishotel.dto.request.RegisterRequestDTO;
import com.thishotel.dto.request.ResetPasswordRequestDTO;
import com.thishotel.enums.BedType;
import com.thishotel.enums.RoomType;
import com.thishotel.exception.InvalidInputException;
import com.thishotel.exception.InvalidTokenException;
import com.thishotel.model.PasswordResetToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomValidationService {

    public void validateRoomCreation(CreateRoomRequestDTO dto) {
        if (dto == null)
            throw new InvalidInputException("Room data cannot be null.", 2000);

        validateRoomNumberNotEmpty(dto.getRoomNumber());
        validateBedTypes(dto.getBedTypes(), dto.getRoomType());
    }

    private void validateRoomNumberNotEmpty(String roomNumber) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new InvalidInputException("Room number: '" + roomNumber +"' cannot be empty.", 2002);
        }
    }


    private void validateBedTypes(List<BedType> bedTypes, RoomType roomType) {
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


}
