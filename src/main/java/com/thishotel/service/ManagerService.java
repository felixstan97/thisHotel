package com.thishotel.service;

import com.thishotel.dto.request.RegisterRequestDTO;
import com.thishotel.mapper.UserMapper;
import com.thishotel.model.Manager;
import com.thishotel.repository.ManagerRepository;
import com.thishotel.validation.UserValidationService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final UserValidationService userValidationService;
    private final UserService userService;
    private final UserMapper userMapper;

    @Transactional
    public Manager registerManager(RegisterRequestDTO dto) {
        userValidationService.validateRegistration(dto);
        userService.ensureEmailNotAlreadyExists(dto.getEmail());
        Manager newManager = userMapper.toManager(dto);
        return managerRepository.save(newManager);
    }

}