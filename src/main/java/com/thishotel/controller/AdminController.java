package com.thishotel.controller;

import com.thishotel.dto.request.RegisterRequestDTO;
import com.thishotel.dto.response.ApiResponseDTO;
import com.thishotel.dto.response.RegistrationResponseDTO;
import com.thishotel.mapper.UserMapper;
import com.thishotel.model.Admin;
import com.thishotel.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
@Validated
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<RegistrationResponseDTO>> registerAdmin (@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        Admin newAdmin = adminService.registerAdmin(registerRequestDTO);
        RegistrationResponseDTO responseData = userMapper.toRegistrationResponseDTO(newAdmin);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(responseData));
    }

}