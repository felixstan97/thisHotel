package com.thishotel.service;

import com.thishotel.dto.request.RegisterRequestDTO;
import com.thishotel.mapper.UserMapper;
import com.thishotel.model.Receptionist;
import com.thishotel.repository.ReceptionistRepository;
import com.thishotel.validation.UserValidationService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceptionistService {

    private final ReceptionistRepository receptionistRepository;
    private final UserValidationService userValidationService;
    private final UserService userService;
    private final UserMapper userMapper;

    @Transactional
    public Receptionist registerReceptionist(RegisterRequestDTO dto) {
        userValidationService.validateRegistration(dto);
        userService.ensureEmailNotAlreadyExists(dto.getEmail());
        Receptionist newReceptionist = userMapper.toReceptionist(dto);
        return receptionistRepository.save(newReceptionist);
    }

}
