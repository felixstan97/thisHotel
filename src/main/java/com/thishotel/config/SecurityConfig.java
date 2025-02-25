package com.thishotel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disabilita CSRF (solo per test, non in produzione!)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/test/**").permitAll()  // Permetti l'accesso agli endpoint di test
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable()) // Disabilita il form di login
                .httpBasic(basic -> basic.disable()); // Disabilita l'autenticazione HTTP Basic

        return http.build();
    }


//     TODO:-> da vedere in futuro la parte dei path e auth
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .antMatchers("/api/test/status").permitAll()  // API pubblica
//                .antMatchers("/api/bookings/**").authenticated()  // API per utenti autenticati
//                .antMatchers("/api/rooms/**").hasRole("EMPLOYEE")  // Solo dipendenti possono aggiungere camere
//                .antMatchers("/api/admin/**").hasRole("ADMIN")  // Solo admin possono accedere
//                .anyRequest().authenticated()  // Tutti gli altri endpoint richiedono l'autenticazione
//                .and()
//                .formLogin()  // Login con form
//                .and()
//                .httpBasic();  // Può essere utile per test (autenticazione via header)
//
//        return http.build();
//    }
}
