package com.thishotel.service;

import com.thishotel.dto.request.LoginRequestDTO;
import com.thishotel.dto.request.ResetPasswordRequestDTO;
import com.thishotel.exception.AuthenticationFailedException;
import com.thishotel.model.PasswordResetToken;
import com.thishotel.model.User;
import com.thishotel.repository.*;
import com.thishotel.security.CustomUserDetailsService;
import com.thishotel.security.JwtUtil;
import com.thishotel.util.PasswordUtil;
import com.thishotel.validation.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final UserValidationService userValidationService;
    private final JwtUtil jwtUtil;

    @Transactional
    public String login(LoginRequestDTO dto) {
        String email = userValidationService.validateEmailNotEmpty(dto.getEmail());
        String password = userValidationService.validatePasswordNotEmpty(dto.getPassword());

        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
            authenticationManager.authenticate(authToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalStateException("User not found after authentication"));
            return jwtUtil.generateToken(userDetails.getUsername(), user.getId());
        } catch (AuthenticationException e) {
            throw new AuthenticationFailedException("Invalid email or password.",1006);
        }
    }

    @Transactional
    public String requestPasswordReset(String email){
        userValidationService.validateEmailNotEmpty(email);

        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            return "If the email exists, a reset link has been sent.";
        }

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, email);
        tokenRepository.save(resetToken);
        return token;
    }


    @Transactional
    public void resetPassword(ResetPasswordRequestDTO dto, String token){
        userValidationService.validateResetTokenNotEmpty(token);

        PasswordResetToken resetToken = tokenRepository.findByToken(token.trim());
        userValidationService.validateResetToken(resetToken, dto.getEmail());
        userValidationService.validatePasswordsMatch(dto);

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalStateException("User not found after token validation"));
        user.setPassword(PasswordUtil.encodePassword(dto.getNewPassword()));
        userRepository.save(user);
        tokenRepository.delete(resetToken);
    }


}
