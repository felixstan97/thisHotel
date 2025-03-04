package com.thishotel.service;

import com.thishotel.dto.RegisterRequestDTO;
import com.thishotel.model.Client;
import com.thishotel.model.User;
import com.thishotel.repository.UserRepository;
import com.thishotel.util.PasswordUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
public class UserService {

    private UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public User registerUser(@Valid RegisterRequestDTO registerRequestDTO) {
        Optional<User> existingUser = userRepository.findByEmail(registerRequestDTO.getEmail());

        if(existingUser.isPresent()){
            throw new RuntimeException("E-Mail already in use.");
        }

        Client client = new Client();
        client.setFirstName(registerRequestDTO.getFirstName());
        client.setLastName(registerRequestDTO.getLastName());
        client.setPhoneNumber(registerRequestDTO.getPhoneNumber());
        client.setDateOfBirth(registerRequestDTO.getDateOfBirth());
        client.setEmail(registerRequestDTO.getEmail());
        client.setPassword(PasswordUtil.encodePassword((registerRequestDTO.getPassword())));
        return userRepository.save(client);
    }


}
