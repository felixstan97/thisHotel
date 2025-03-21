package com.thishotel.controller;

import com.thishotel.dto.request.RegisterRequestDTO;
import com.thishotel.dto.response.ApiResponseDTO;
import com.thishotel.dto.response.RegistrationResponseDTO;
import com.thishotel.mapper.UserMapper;
import com.thishotel.model.Cleaner;
import com.thishotel.model.Manager;
import com.thishotel.model.Receptionist;
import com.thishotel.service.CleanerService;
import com.thishotel.service.ManagerService;
import com.thishotel.service.ReceptionistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("api/admin/staff")
@RequiredArgsConstructor
public class StaffController {

    private final ReceptionistService receptionistService;
    private final ManagerService managerService;
    private final CleanerService cleanerService;
    private final UserMapper userMapper;

    @PostMapping("/register-manager")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO<RegistrationResponseDTO>> registerManager(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        Manager newManager = managerService.registerManager(registerRequestDTO);
        RegistrationResponseDTO responseData = userMapper.toRegistrationResponseDTO(newManager);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(responseData));
    }

    @PostMapping("/register-receptionist")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO<RegistrationResponseDTO>> registerReceptionist (@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        Receptionist newReceptionist = receptionistService.registerReceptionist(registerRequestDTO);
        RegistrationResponseDTO responseData = userMapper.toRegistrationResponseDTO(newReceptionist);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(responseData));
    }

    @PostMapping("/register-cleaner")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO<RegistrationResponseDTO>> registerCleaner (@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        Cleaner newCleaner = cleanerService.registerCleaner(registerRequestDTO);
        RegistrationResponseDTO responseData = userMapper.toRegistrationResponseDTO(newCleaner);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(responseData));
    }

}

//  TODO: 1) getListShiftEqualsToBeAssigned
//  TODO: 2) setShift