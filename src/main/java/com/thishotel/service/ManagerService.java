package com.thishotel.service;

import com.thishotel.dto.RegisterRequestDTO;
import com.thishotel.mapper.UserMapper;
import com.thishotel.model.Manager;
import com.thishotel.repository.ManagerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final UserMapper userMapper;


    @Autowired
    public ManagerService(ManagerRepository managerRepository, UserMapper userMapper){
        this.managerRepository = managerRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public Manager registerManager(RegisterRequestDTO registerRequestDTO){
        Optional<Manager> existingManager = managerRepository.findByEmail(registerRequestDTO.getEmail());

        if (existingManager.isPresent()) {
            throw new RuntimeException("E-Mail already exists.");
        }

        Manager newManager = userMapper.toManager(registerRequestDTO);
        return managerRepository.save(newManager);
    }

}
