package com.thishotel.service;

import com.thishotel.dto.request.RegisterRequestDTO;
import com.thishotel.exception.OnlyOneAdminAllowedException;
import com.thishotel.mapper.UserMapper;
import com.thishotel.model.Admin;
import com.thishotel.repository.AdminRepository;
import com.thishotel.validation.UserValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserValidationService userValidationService;
    private final AdminRepository adminRepository;
    private final UserMapper userMapper;

    @Transactional
    public Admin registerAdmin (RegisterRequestDTO dto){
        userValidationService.validateRegistration(dto);

        if(adminRepository.count() > 0)
            throw new OnlyOneAdminAllowedException("Registration not allowed at this time", 1002);

        Admin newAdmin = userMapper.toAdmin(dto);
        return adminRepository.save(newAdmin);
    }

}



