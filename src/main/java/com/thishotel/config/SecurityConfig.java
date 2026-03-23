package com.thishotel.config;

import com.thishotel.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtFilter jwtFilter, UserDetailsService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"/api/admin/register").permitAll()  // Permetti la registrazione dell'admin
                        .requestMatchers("/api/auth/**","/test/status").permitAll() // API pubbliche
                        .requestMatchers("/api/client/register").permitAll()  //Client OPS
                        .requestMatchers("/api/admin/rooms/**").hasAnyRole("RECEPTIONIST", "MANAGER", "ADMIN")
                        .requestMatchers("/api/bookings/**", "api/profile/").hasAnyRole("CLIENT", "RECEPTIONIST", "MANAGER", "ADMIN")
                        .requestMatchers("/api/employees/**").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers("/api/cleaning-tasks/**").hasAnyRole("CLEANER", "MANAGER","ADMIN")
                        .requestMatchers("/api/reports/**").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers("/api/users/**").hasRole("ADMIN") // Solo gli admin possono gestire utenti
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

//         JWT Filter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173"); // Permetti il frontend
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}

