package com.thishotel.controller;

import com.thishotel.dto.request.AssignShiftRequestDTO;
import com.thishotel.dto.request.RegisterRequestDTO;
import com.thishotel.dto.response.ApiResponseDTO;
import com.thishotel.dto.response.RegistrationResponseDTO;
import com.thishotel.dto.response.UserResponseDto;
import com.thishotel.mapper.UserMapper;
import com.thishotel.model.Cleaner;
import com.thishotel.model.Manager;
import com.thishotel.model.Receptionist;
import com.thishotel.model.User;
import com.thishotel.service.CleanerService;
import com.thishotel.service.ManagerService;
import com.thishotel.service.ReceptionistService;
import com.thishotel.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("api/users")
@RequiredArgsConstructor
public class StaffController {

    private final ReceptionistService receptionistService;
    private final ManagerService managerService;
    private final CleanerService cleanerService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register-manager")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO<RegistrationResponseDTO>> registerManager(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        Manager newManager = managerService.registerManager(registerRequestDTO);
        RegistrationResponseDTO responseData = userMapper.toRegistrationResponseDTO(newManager);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(responseData));
    }

    @PostMapping("/register-receptionist")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO<RegistrationResponseDTO>> registerReceptionist(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        Receptionist newReceptionist = receptionistService.registerReceptionist(registerRequestDTO);
        RegistrationResponseDTO responseData = userMapper.toRegistrationResponseDTO(newReceptionist);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(responseData));
    }

    @PostMapping("/register-cleaner")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO<RegistrationResponseDTO>> registerCleaner(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        Cleaner newCleaner = cleanerService.registerCleaner(registerRequestDTO);
        RegistrationResponseDTO responseData = userMapper.toRegistrationResponseDTO(newCleaner);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(responseData));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO<List<UserResponseDto>>> getAllStaff(HttpServletRequest request){
        List<User> userList = userService.getUsers(request);
        List<UserResponseDto> responseDto = userMapper.toResponseDto(userList);
        String message = responseDto.isEmpty() ? "No other staff members found." : "Users retrieved successfully.";
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(responseDto,message));
    }

    @GetMapping("/shift-to-assign")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO<List<UserResponseDto>>> getStaffWithShiftToAssign() {
        List<User> userList = userService.getUserWithShiftToAssign();
        List<UserResponseDto> responseList = userMapper.toResponseDto(userList);
        String message = responseList.isEmpty() ? "No staff with shift 'to be assigned' found." : "Staff retrived successfully";
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(responseList, message));
    }

    @PatchMapping("/shifts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO> assignShifts(@Valid @RequestBody List<AssignShiftRequestDTO> requestDTO) {
        userService.assignShiftsToStaff(requestDTO);
        String message = "Shifts assigned successfully.";
    return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(null, message));
    }

    @PatchMapping("/shift")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO<UserResponseDto>> assignShift(@Valid @RequestBody AssignShiftRequestDTO requestDTO) {
        User user = userService.assignShift(requestDTO.getId(), requestDTO.getShift());
        UserResponseDto response = userMapper.toResponseDto(user);
        String message = "Shift assigned successfully.";
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(response, message));
    }

}

//  TODO: 3) -> ha senso mettere un individualBadgeCode o qualcosa del genere da mostrare nella lista dei dipendenti
