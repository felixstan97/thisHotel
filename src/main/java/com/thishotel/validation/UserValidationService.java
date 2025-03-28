package com.thishotel.validation;

import com.thishotel.dto.request.RegisterRequestDTO;
import com.thishotel.dto.request.ResetPasswordRequestDTO;
import com.thishotel.exception.InvalidInputException;
import com.thishotel.exception.InvalidTokenException;
import com.thishotel.model.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class UserValidationService {

    public void validateRegistration(RegisterRequestDTO dto) {
        if (dto == null)
            throw new InvalidInputException("Registration data cannot be null.", 1007);

        validateEmailNotEmpty(dto.getEmail());
        validatePasswordNotEmpty(dto.getPassword());
    }

    public String validateEmailNotEmpty(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidInputException("Email cannot be empty.", 1001);
        }
        return email.trim();
    }

    public String validatePasswordNotEmpty(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new InvalidInputException("Password cannot be empty.", 1002);
        }
        return password.trim();
    }

    public void validateResetTokenNotEmpty(String token) {
        if(token == null || token.trim().isEmpty()) {
            throw new InvalidTokenException("Reset token is required.", 1003);
        }
    }

    public void validateEmailNotAlreadyExists(boolean alreadyExists) {
        if (alreadyExists) {
            throw new IllegalArgumentException("Email already in use.");
        }
    }

    public void validateResetToken(PasswordResetToken resetToken, String email) {
        if (resetToken == null || resetToken.isExpired() || !resetToken.getEmail().equals(email)) {
            throw new InvalidTokenException("Invalid or expired token.", 1004);
        }
    }

    public void validatePasswordsMatch(ResetPasswordRequestDTO dto) {
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new InvalidInputException("Passwords do not match.", 1005);
        }
    }

    public void validateAccessPermission(Long loggedId, Long id) {
        if (!loggedId.equals(id)) {
            throw new AccessDeniedException("You can only access your own profile.");
        }
    }

    public void validateShiftAssignement(User user, Long id) {
        if (!(user instanceof Manager || user instanceof Receptionist || user instanceof Cleaner)) {
            throw new IllegalArgumentException("User: '" + id + "' dose not support shift assignement.");
        }
    }

}
