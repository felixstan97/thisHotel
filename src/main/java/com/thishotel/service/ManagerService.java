package com.thishotel.service;

import com.thishotel.dto.RegisterRequestDTO;
import com.thishotel.model.Manager;
import com.thishotel.repository.ManagerRepository;
import com.thishotel.util.PasswordUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ManagerService {

    private ManagerRepository managerRepository;

    @Autowired
    public ManagerService(ManagerRepository managerRepository){
        this.managerRepository = managerRepository;
    }

    @Transactional
    public Manager registerManager(RegisterRequestDTO registerRequestDTO){
        Optional<Manager> alreadyExistManager = managerRepository.findByEmail(registerRequestDTO.getEmail());

        if (alreadyExistManager.isPresent()) {
            throw new RuntimeException("E-Mail already exists.");
        }

        Manager newManager = new Manager();
        newManager.setFirstName(registerRequestDTO.getFirstName());
        newManager.setLastName(registerRequestDTO.getLastName());
        newManager.setPhoneNumber(registerRequestDTO.getPhoneNumber());
        newManager.setDateOfBirth(registerRequestDTO.getDateOfBirth());
        newManager.setEmail(registerRequestDTO.getEmail());
        newManager.setPassword(PasswordUtil.encodePassword(registerRequestDTO.getPassword()));
        newManager.setCreatedAt(LocalDateTime.now());
        newManager.setUpdatedAt(LocalDateTime.now());
        newManager.setActive(true);
        return managerRepository.save(newManager);
    }

}
