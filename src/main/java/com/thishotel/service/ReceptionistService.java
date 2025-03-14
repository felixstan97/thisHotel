package com.thishotel.service;

import com.thishotel.dto.request.RegisterRequestDTO;
import com.thishotel.exception.EmailAlreadyExistsException;
import com.thishotel.mapper.UserMapper;
import com.thishotel.model.Receptionist;
import com.thishotel.repository.ReceptionistRepository;
import com.thishotel.validation.UserValidationService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReceptionistService {

    private final ReceptionistRepository receptionistRepository;
    private final UserValidationService userValidationService;
    private final UserMapper userMapper;

    @Transactional
    public Receptionist registerReceptionist(RegisterRequestDTO dto) {
        userValidationService.validateRegistration(dto);

        Optional<Receptionist> existingReceptionist = receptionistRepository.findByEmail(dto.getEmail());
        if (existingReceptionist.isPresent()) {
            throw new EmailAlreadyExistsException(dto.getEmail(), 1001);
        }

        Receptionist newReceptionist = userMapper.toReceptionist(dto);
        return receptionistRepository.save(newReceptionist);
    }

}
