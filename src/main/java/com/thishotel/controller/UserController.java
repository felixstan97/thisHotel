package com.thishotel.controller;

import com.thishotel.dto.request.UpdateProfileRequestDTO;
import com.thishotel.dto.response.ApiResponseDTO;
import com.thishotel.dto.response.UserDetailResponseDTO;
import com.thishotel.mapper.UserMapper;
import com.thishotel.model.User;
import com.thishotel.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/profile")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserDetailResponseDTO>> getProfile(@PathVariable Long id, HttpServletRequest request) {
        User user = userService.getUserProfile(id, request);
        UserDetailResponseDTO response = userMapper.toUserDetailResponseDTO(user);
        String message = "Profile retrieved successfully.";
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(response, message));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserDetailResponseDTO>> updateProfile(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProfileRequestDTO requestDTO,
            HttpServletRequest request) {
        User updatedUser = userService.updateUserProfile(id, requestDTO, request);
        UserDetailResponseDTO responseDTO = userMapper.toUserDetailResponseDTO(updatedUser);
        String message = "Profile updated successfully.";
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(responseDTO, message));
    }

}
