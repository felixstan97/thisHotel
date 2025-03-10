package com.thishotel.service;

import com.thishotel.dto.request.LoginRequestDTO;
import com.thishotel.dto.request.ResetPasswordRequestDTO;
import com.thishotel.dto.request.ResetPasswordRequestEmailDTO;
import com.thishotel.exception.AuthenticationFailedException;
import com.thishotel.exception.InvalidTokenException;
import com.thishotel.model.PasswordResetToken;
import com.thishotel.model.User;
import com.thishotel.repository.*;
import com.thishotel.security.CustomUserDetailsService;
import com.thishotel.security.JwtUtil;
import com.thishotel.util.PasswordUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthService(
            UserRepository userRepository,
            PasswordResetTokenRepository tokenRepository,
            AuthenticationManager authenticationManager,
            CustomUserDetailsService userDetailsService,
            JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public String login(LoginRequestDTO dto) {
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    dto.getEmail().trim(), dto.getPassword().trim());
            authenticationManager.authenticate(authToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getEmail().trim());
            return jwtUtil.generateToken(userDetails.getUsername());
        } catch (AuthenticationException e) {
            throw new AuthenticationFailedException("Invalid email or password.",1006);
        }
    }

    @Transactional
    public String requestPasswordReset(String email){
        if (email == null || email.trim().isEmpty())
            throw new IllegalArgumentException("Email cannot be empty");

        Optional<User> user = userRepository.findByEmail(email);

        if (!user.isPresent())
            return "If the email exists, a reset link has been sent.";

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, email);
        tokenRepository.save(resetToken);
        return token;
    }


    @Transactional
    public void resetPassword(ResetPasswordRequestDTO dto, String token){
        if(token == null || token.trim().isEmpty())
            throw new InvalidTokenException("Reset token is required.", 1003);

        PasswordResetToken resetToken = tokenRepository.findByToken(token.trim());

        if(resetToken == null || resetToken.isExpired() || !resetToken.getEmail().equals(dto.getEmail()))
            throw new InvalidTokenException("Invalid or expired token.", 1004);

        if (!dto.getNewPassword().equals(dto.getConfirmPassword()))
            throw new IllegalArgumentException("Passwords do not match.");

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalStateException("User not found alfter token validation"));

        user.setPassword(PasswordUtil.encodePassword(dto.getNewPassword()));
        userRepository.save(user);
        tokenRepository.delete(resetToken);
    }


}
