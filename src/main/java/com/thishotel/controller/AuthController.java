package com.thishotel.controller;

import com.thishotel.dto.LoginRequestDTO;
import com.thishotel.security.CustomUserDetailsService;
import com.thishotel.security.JwtUtil;
import com.thishotel.util.PasswordUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            CustomUserDetailsService userDetailsService,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<String> createToken(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        try {
            // Autenticazione dell'utente con email e password
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

            authenticationManager.authenticate(authenticationToken);

            // Otteniamo i dettagli dell'utente (email) dal database
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDTO.getEmail());


            // Generiamo il token JWT
            String jwt = jwtUtil.generateToken(userDetails.getUsername());

            return ResponseEntity.ok(jwt);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

    }


}
