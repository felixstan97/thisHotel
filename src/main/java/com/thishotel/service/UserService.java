package com.thishotel.service;

import com.thishotel.dto.request.AssignShiftRequestDTO;
import com.thishotel.dto.request.UpdateProfileRequestDTO;
import com.thishotel.enums.Shift;
import com.thishotel.exception.UserNotFoundException;
import com.thishotel.model.Cleaner;
import com.thishotel.model.Manager;
import com.thishotel.model.Receptionist;
import com.thishotel.model.User;
import com.thishotel.repository.UserRepository;
import com.thishotel.security.JwtUtil;
import com.thishotel.util.ServiceUtil;
import com.thishotel.validation.UserValidationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserValidationService userValidationService;
    private final UserRepository userRepository;
    private final ServiceUtil serviceUtil;
    private final JwtUtil jwtUtil;

    public List<User> getUsers(HttpServletRequest request){
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new IllegalStateException("No users found.");
        }

        Long adminId = getUserIdFromRequest(request);

        return users.stream().filter(user -> !user.getId().equals(adminId)).toList();
    }


    public List<User> getUserWithShiftToAssign() {
        return userRepository.findAllStaffWithShiftToBeAssigned();
    }

    @Transactional
    public User updateUserProfile(Long id, UpdateProfileRequestDTO dto, HttpServletRequest request){
        Long loggedInUserId = getUserIdFromRequest(request);
        userValidationService.validateAccessPermission(loggedInUserId, id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found.", 1020));

        if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail())) {
            userValidationService.validateEmailNotAlreadyExists(userRepository.existsByEmail(dto.getEmail()));
        }

        serviceUtil.updateIfNotNull(dto.getEmail(), user::setEmail);
        serviceUtil.updateIfNotNull(dto.getFirstName(), user::setFirstName);
        serviceUtil.updateIfNotNull(dto.getLastName(), user::setLastName);
        serviceUtil.updateIfNotNull(dto.getDateOfBirth(), user::setDateOfBirth);
        serviceUtil.updateIfNotNull(dto.getPhoneNumber(), user::setPhoneNumber);

        return userRepository.save(user);
    }

    public User getUserProfile(Long id, HttpServletRequest request) {
        Long loggedInUserId = getUserIdFromRequest(request);
        userValidationService.validateAccessPermission(loggedInUserId, id);

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found", 1020));
    }

    @Transactional
    public User assignShift(Long id, Shift shift) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found.", 1020));

        userValidationService.validateShiftAssignement(user, id);

        setShift(user, shift);
        return userRepository.save(user);
    }

    @Transactional
    public List<User> assignShiftsToStaff(List<AssignShiftRequestDTO> requestDto) {
        List<User> users = requestDto.stream().map(dto -> {
            User user = userRepository.findById(dto.getId())
                    .orElseThrow(() -> new UserNotFoundException("User with id: '" + dto.getId()+ "' not found.", 1020));

            userValidationService.validateShiftAssignement(user, dto.getId());

            setShift(user, dto.getShift());
            return user;
        }).toList();
        return userRepository.saveAll(users);
    }

    private void setShift (User user, Shift shift) {
        if (user instanceof Manager manager) {
            manager.setShift(shift);
        } else if (user instanceof Receptionist receptionist) {
            receptionist.setShift(shift);
        } else if (user instanceof Cleaner cleaner) {
            cleaner.setShift(shift);
        }
    }

    private Long getUserIdFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return jwtUtil.extractId(token);
        }
        throw new IllegalStateException("No valid token found in request.");
    }


}
