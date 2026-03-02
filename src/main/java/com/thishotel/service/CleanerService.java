package com.thishotel.service;

import com.thishotel.dto.request.RegisterRequestDTO;
import com.thishotel.mapper.UserMapper;
import com.thishotel.model.Cleaner;
import com.thishotel.repository.CleanerRepository;
import com.thishotel.validation.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CleanerService {

    private final UserValidationService userValidationService;
    private final CleanerRepository cleanerRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    @Transactional
    public Cleaner registerCleaner(RegisterRequestDTO dto) {
        userValidationService.validateRegistration(dto);
        userService.ensureEmailNotAlreadyExists(dto.getEmail());
        Cleaner newCleaner = userMapper.toCleaner(dto);
        return cleanerRepository.save(newCleaner);
    }


}
