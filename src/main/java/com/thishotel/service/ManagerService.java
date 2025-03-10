package com.thishotel.service;

import com.thishotel.dto.request.RegisterRequestDTO;
import com.thishotel.exception.EmailAlreadyExistsException;
import com.thishotel.mapper.UserMapper;
import com.thishotel.model.Manager;
import com.thishotel.repository.ManagerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final UserMapper userMapper;

    public ManagerService(ManagerRepository managerRepository, UserMapper userMapper) {
        this.managerRepository = managerRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public Manager registerManager(RegisterRequestDTO registerRequestDTO) {
        Optional<Manager> existingManager = managerRepository.findByEmail(registerRequestDTO.getEmail());

        if (existingManager.isPresent())
            throw new EmailAlreadyExistsException(registerRequestDTO.getEmail(), 1001);

        Manager newManager = userMapper.toManager(registerRequestDTO);
        return managerRepository.save(newManager);
    }

}


