package com.thishotel.controller;

import com.thishotel.dto.request.RegisterRequestDTO;
import com.thishotel.dto.response.ApiResponseDTO;
import com.thishotel.dto.response.RegistrationResponseDTO;
import com.thishotel.mapper.UserMapper;
import com.thishotel.model.Admin;
import com.thishotel.model.Manager;
import com.thishotel.model.Receptionist;
import com.thishotel.service.AdminService;
import com.thishotel.service.ManagerService;
import com.thishotel.service.ReceptionistService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
@Validated
public class AdminController {

    private final AdminService adminService;
    private final ManagerService managerService;
    private final ReceptionistService receptionistService;
    private final UserMapper userMapper;

    public AdminController(AdminService adminService,
                           ManagerService managerService,
                           ReceptionistService receptionistService,
                           UserMapper userMapper){
        this.adminService = adminService;
        this.managerService = managerService;
        this.receptionistService = receptionistService;
        this.userMapper = userMapper;
    }

//    admin first registration
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<RegistrationResponseDTO>> registerAdmin (@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        Admin newAdmin = adminService.registerAdmin(registerRequestDTO);
        RegistrationResponseDTO responseData = userMapper.toRegistrationResponseDTO(newAdmin);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(responseData));
    }

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

}