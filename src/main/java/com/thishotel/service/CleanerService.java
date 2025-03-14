package com.thishotel.service;

import com.thishotel.dto.request.RegisterRequestDTO;
import com.thishotel.exception.EmailAlreadyExistsException;
import com.thishotel.mapper.UserMapper;
import com.thishotel.model.Cleaner;
import com.thishotel.repository.CleanerRepository;
import com.thishotel.validation.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CleanerService {

    private final UserValidationService userValidationService;
    private final CleanerRepository cleanerRepository;
    private final UserMapper userMapper;

    @Transactional
    public Cleaner registerCleaner(RegisterRequestDTO dto) {
        userValidationService.validateRegistration(dto);

        Optional<Cleaner> existingCleaner = cleanerRepository.findByEmail(dto.getEmail());
        if (existingCleaner.isPresent()) {
            throw new EmailAlreadyExistsException(dto.getEmail(), 1001);
        }

        Cleaner newCleaner = userMapper.toCleaner(dto);
        return cleanerRepository.save(newCleaner);
    }


}
