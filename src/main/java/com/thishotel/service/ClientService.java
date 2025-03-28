package com.thishotel.service;

import com.thishotel.dto.request.RegisterRequestDTO;
import com.thishotel.exception.EmailAlreadyExistsException;
import com.thishotel.mapper.UserMapper;
import com.thishotel.model.Client;
import com.thishotel.repository.ClientRepository;
import com.thishotel.validation.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final UserValidationService userValidationService;
    private final ClientRepository clientRepository;
    private final UserMapper userMapper;

    @Transactional
    public Client registerClient(RegisterRequestDTO dto) {
        userValidationService.validateRegistration(dto);

        Optional<Client> existingClient = clientRepository.findByEmail(dto.getEmail());
        if (existingClient.isPresent()) {
            throw new EmailAlreadyExistsException(dto.getEmail(), 1001);
        }

        Client newClient = userMapper.toClient(dto);
        return clientRepository.save(newClient);
    }


}
