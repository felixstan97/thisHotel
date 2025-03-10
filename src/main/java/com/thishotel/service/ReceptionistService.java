package com.thishotel.service;

import com.thishotel.dto.request.RegisterRequestDTO;
import com.thishotel.exception.EmailAlreadyExistsException;
import com.thishotel.mapper.UserMapper;
import com.thishotel.model.Receptionist;
import com.thishotel.repository.ReceptionistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReceptionistService {

    private ReceptionistRepository receptionistRepository;
    private UserMapper userMapper;

    public ReceptionistService(ReceptionistRepository receptionistRepository, UserMapper userMapper) {
        this.receptionistRepository = receptionistRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public Receptionist registerReceptionist(RegisterRequestDTO registerRequestDTO) {
        Optional<Receptionist> existingReceptionist = receptionistRepository.findByEmail(registerRequestDTO.getEmail());

        if (existingReceptionist.isPresent())
            throw new EmailAlreadyExistsException(registerRequestDTO.getEmail(), 1001);

        Receptionist newReceptionist = userMapper.toReceptionist(registerRequestDTO);
        return receptionistRepository.save(newReceptionist);
    }

}
