package com.thishotel.mapper;

import com.thishotel.dto.request.RegisterRequestDTO;
import com.thishotel.dto.response.RegistrationResponseDTO;
import com.thishotel.model.Admin;
import com.thishotel.model.Manager;
import com.thishotel.model.Receptionist;
import com.thishotel.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "password", expression = "java(com.thishotel.util.PasswordUtil.encodePassword(dto.getPassword()))")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    Admin toAdmin(RegisterRequestDTO dto);

    @Mapping(target = "password", expression = "java(com.thishotel.util.PasswordUtil.encodePassword(dto.getPassword()))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "shift", ignore = true)
    Manager toManager(RegisterRequestDTO dto);

    @Mapping(target = "password", expression = "java(com.thishotel.util.PasswordUtil.encodePassword(dto.getPassword()))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "shift", ignore = true)
    Receptionist toReceptionist(RegisterRequestDTO dto);

    @Mapping(target = "role", expression = "java(user.getClass().getSimpleName().toUpperCase())")
    RegistrationResponseDTO toRegistrationResponseDTO(User user);

}
