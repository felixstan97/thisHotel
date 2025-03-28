package com.thishotel.controller;

import com.thishotel.dto.request.RegisterRequestDTO;
import com.thishotel.dto.response.ApiResponseDTO;
import com.thishotel.dto.response.RegistrationResponseDTO;
import com.thishotel.mapper.UserMapper;
import com.thishotel.model.Client;
import com.thishotel.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<RegistrationResponseDTO>> registerClient(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        Client newClient = clientService.registerClient(registerRequestDTO);
        RegistrationResponseDTO response = userMapper.toRegistrationResponseDTO(newClient);
        String message = "Client registered successfully";
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(response, message));
    }

}
