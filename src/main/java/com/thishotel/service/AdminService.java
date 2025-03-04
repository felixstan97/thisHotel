package com.thishotel.service;

import com.thishotel.dto.RegisterRequestDTO;
import com.thishotel.model.Admin;
import com.thishotel.repository.AdminRepository;
import com.thishotel.util.PasswordUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminService {

    private AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

    @Transactional
    public Admin regiterAdmin (RegisterRequestDTO registerRequestDTO){

        if(adminRepository.count() > 0) {
            throw new RuntimeException("Admin already exists in the system");
        }

        Admin newAdmin = new Admin();
        newAdmin.setFirstName(registerRequestDTO.getFirstName());
        newAdmin.setLastName(registerRequestDTO.getLastName());
        newAdmin.setPhoneNumber(registerRequestDTO.getPhoneNumber());
        newAdmin.setDateOfBirth(registerRequestDTO.getDateOfBirth());
        newAdmin.setEmail(registerRequestDTO.getEmail());
        newAdmin.setPassword(PasswordUtil.encodePassword(registerRequestDTO.getPassword()));
        newAdmin.setCreatedAt(LocalDateTime.now());
        newAdmin.setUpdatedAt(LocalDateTime.now());
        newAdmin.setActive(true);
        return adminRepository.save(newAdmin);
    }

}

/**     todo -> da capire se ha senso implementare  existsByRole("ADMIN")
 * public Admin createAdmin(AdminRequestDTO request) {
 *         if (userRepository.existsByRole("ADMIN")) {
 *             throw new IllegalStateException("Un admin esiste già!");
 *         }
 *         if (userRepository.existsByEmail(request.getEmail())) {
 *             throw new IllegalStateException("Email già in uso!");
 *         }
 *         if (userRepository.existsByUsername(request.getUsername())) {
 *             throw new IllegalStateException("Username già in uso!");
 *         }
 *
 *         Admin admin = new Admin();
 *         admin.setUsername(request.getUsername());
 *         admin.setEmail(request.getEmail());
 *         admin.setPassword(passwordEncoder.encode(request.getPassword()));
 *
 *         return userRepository.save(admin);
 *     }
 */



