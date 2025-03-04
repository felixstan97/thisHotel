package com.thishotel.controller;

import com.thishotel.dto.RegisterRequestDTO;
import com.thishotel.model.Admin;
import com.thishotel.model.Manager;
import com.thishotel.security.JwtUtil;
import com.thishotel.service.AdminService;
import com.thishotel.service.ManagerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
@Validated
public class AdminController {

    private AdminService adminService;

    private ManagerService managerService;

    private final JwtUtil jwtUtil;
    @Autowired
    public AdminController(AdminService adminService,
                           ManagerService managerService,
                           JwtUtil jwtUtil){
        this.adminService = adminService;
        this.managerService = managerService;
        this.jwtUtil = jwtUtil;
    }

//    admin first registration
    @PostMapping("/register")
    public ResponseEntity<String> registerAdmin (@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        try {
            Admin newAdmin = adminService.regiterAdmin(registerRequestDTO);
            String token = jwtUtil.generateToken(newAdmin.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body("Admin created. Token: " + token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }

    }

    @PostMapping("/register-manager")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> registerManagerByAdmin(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        try {
            Manager newManager = managerService.registerManager(registerRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Manager created.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }


}

//    other admin's operations

//    @PostMapping("/create-user")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<User>




//}
