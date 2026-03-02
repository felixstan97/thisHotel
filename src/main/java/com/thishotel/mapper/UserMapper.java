package com.thishotel.mapper;

import com.thishotel.dto.request.RegisterRequestDTO;
import com.thishotel.dto.response.RegistrationResponseDTO;
import com.thishotel.dto.response.UserDetailResponseDTO;
import com.thishotel.dto.response.UserResponseDto;
import com.thishotel.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


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

    @Mapping(target = "password", expression = "java(com.thishotel.util.PasswordUtil.encodePassword(dto.getPassword()))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "shift", ignore = true)
    Cleaner toCleaner(RegisterRequestDTO dto);

    @Mapping(target = "password", expression = "java(com.thishotel.util.PasswordUtil.encodePassword(dto.getPassword()))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "active", constant = "true")
    Client toClient(RegisterRequestDTO dto);

    @Mapping(target = "role", expression = "java(user.getClass().getSimpleName().toUpperCase())")
    RegistrationResponseDTO toRegistrationResponseDTO(User user);

    @Mapping(target = "role", expression = "java(user.getClass().getSimpleName().toUpperCase())")
    UserResponseDto toResponseDto(User user);

    List<UserResponseDto> toResponseDto(List<User> user);

    @Mapping(target = "role", expression = "java(user.getClass().getSimpleName().toUpperCase())")
    UserDetailResponseDTO toUserDetailResponseDTO(User user);
}
