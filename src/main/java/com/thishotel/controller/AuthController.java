package com.thishotel.controller;

import com.thishotel.dto.request.LoginRequestDTO;
import com.thishotel.dto.request.ResetPasswordRequestDTO;
import com.thishotel.dto.request.ResetPasswordRequestEmailDTO;
import com.thishotel.dto.response.ApiResponseDTO;
import com.thishotel.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;


    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<String>> createToken(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        String token = authService.login(loginRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(token));
    }

    @PostMapping("/reset-password-request")
    public ResponseEntity<ApiResponseDTO<String>> requestPasswordReset(@RequestBody ResetPasswordRequestEmailDTO emailDTO) {
        String result = authService.requestPasswordReset(emailDTO.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(result));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponseDTO<String>> resetPassword(
            @Valid @RequestBody ResetPasswordRequestDTO dto,
            @RequestHeader(value = "X-Reset-Token", required = false) String token) {
        authService.resetPassword(dto, token);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>("Password reset successful"));
    }


}
