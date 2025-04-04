package com.thishotel.service;

import com.thishotel.dto.request.RegisterRequestDTO;
import com.thishotel.exception.EmailAlreadyExistsException;
import com.thishotel.mapper.UserMapper;
import com.thishotel.model.Manager;
import com.thishotel.repository.ManagerRepository;
import com.thishotel.validation.UserValidationService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final UserValidationService userValidationService;
    private final UserMapper userMapper;

    @Transactional
    public Manager registerManager(RegisterRequestDTO dto) {
        userValidationService.validateRegistration(dto);

        Optional<Manager> existingManager = managerRepository.findByEmail(dto.getEmail());
        if (existingManager.isPresent()) {
            throw new EmailAlreadyExistsException(dto.getEmail(), 1001);
        }

        Manager newManager = userMapper.toManager(dto);
        return managerRepository.save(newManager);
    }

}


