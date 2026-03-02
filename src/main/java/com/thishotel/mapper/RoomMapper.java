package com.thishotel.mapper;

import com.thishotel.dto.request.CreateRoomRequestDTO;
import com.thishotel.dto.response.RoomDetailResponseDTO;
import com.thishotel.dto.response.RoomResponseDTO;
import com.thishotel.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isDisabledFriendly", defaultValue = "false")
    @Mapping(target = "isSuite", defaultValue = "false")
    @Mapping(target = "hasBalcony", defaultValue = "false")
    @Mapping(target = "hasTerrace", defaultValue = "false")
    Room toRoom(CreateRoomRequestDTO dto);

    RoomResponseDTO toRoomResponseDTO(Room room);

    RoomDetailResponseDTO toRoomDetailResponseDTO(Room room);

}
