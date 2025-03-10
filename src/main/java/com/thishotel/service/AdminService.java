package com.thishotel.service;

import com.thishotel.dto.request.RegisterRequestDTO;
import com.thishotel.exception.OnlyOneAdminAllowedException;
import com.thishotel.mapper.UserMapper;
import com.thishotel.model.Admin;
import com.thishotel.repository.AdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserMapper userMapper;

    public AdminService(AdminRepository adminRepository, UserMapper userMapper){
        this.adminRepository = adminRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public Admin registerAdmin (RegisterRequestDTO registerRequestDTO){
        if(adminRepository.count() > 0)
            throw new OnlyOneAdminAllowedException("Registration not allowed at this time", 1002);

        Admin newAdmin = userMapper.toAdmin(registerRequestDTO);
        return adminRepository.save(newAdmin);
    }

}



