package com.thishotel.config;

import com.thishotel.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private final JwtFilter jwtFilter;

    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtFilter jwtFilter, UserDetailsService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
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


    // Aggiunta configurazione CORS
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Permette cookie o auth headers
        config.addAllowedOrigin("http://localhost:3000"); // Consenti il frontend locale
        config.addAllowedHeader("*"); // Consenti tutti gli header
        config.addAllowedMethod("*"); // Consenti tutti i metodi (GET, POST, ecc.)
        source.registerCorsConfiguration("/**", config); // Applica a tutti gli endpoint
        return new CorsFilter(source);
    }


}





//BACK UPS


/**  todo --> versione da usare da sola per escludere il discorso dei TOKEN
 *
 *  @Bean
 *     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
 *         http
 *                 .csrf(csrf -> csrf.disable())
 *                 .authorizeHttpRequests(auth -> auth
 *                         .requestMatchers("/api/auth/**", "/api/rooms").permitAll() // API pubbliche
 *                         .requestMatchers("/api/bookings/**").hasAnyRole("CLIENT", "RECEPTIONIST", "MANAGER", "ADMIN")
 *                         .requestMatchers("/api/employees/**").hasAnyRole("MANAGER", "ADMIN")
 *                         .requestMatchers("/api/cleaning-tasks/**").hasAnyRole("CLEANER", "MANAGER")
 *                         .requestMatchers("/api/reports/**").hasAnyRole("MANAGER", "ADMIN")
 *                         .requestMatchers("/api/users/**").hasRole("ADMIN") // Solo gli admin possono gestire utenti
 *                         .anyRequest().authenticated()
 *                 )
 *                 .formLogin(form -> form.disable())
 *                 .httpBasic(basic -> basic.disable());
 *
 *         return http.build();
 *     }
 */




//  S TODO --> SALVAVITA utile nel caso il routing non vada bene.
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())  // Disabilita CSRF (solo per test, non in produzione!)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/test/**").permitAll()  // Permetti l'accesso agli endpoint di test
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form.disable()) // Disabilita il form di login
//                .httpBasic(basic -> basic.disable()); // Disabilita l'autenticazione HTTP Basic
//
//        return http.build();
//    }

//     TODO:-> da vedere in futuro la parte dei path e auth
    //***************************************************
    /**
     *    @Bean
     *     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     *         http
     *             .csrf(csrf -> csrf.disable())
     *             .authorizeHttpRequests(auth -> auth
     *                 .requestMatchers("/api/auth/**", "/api/rooms").permitAll() // API pubbliche
     *                 .requestMatchers("/api/bookings/**").hasAnyRole("CLIENT", "RECEPTIONIST", "MANAGER", "ADMIN")
     *                 .requestMatchers("/api/employees/**").hasAnyRole("MANAGER", "ADMIN")
     *                 .requestMatchers("/api/cleaning-tasks/**").hasAnyRole("CLEANER", "MANAGER")
     *                 .requestMatchers("/api/reports/**").hasAnyRole("MANAGER", "ADMIN")
     *                 .requestMatchers("/api/users/**").hasRole("ADMIN") // Solo gli admin possono gestire utenti
     *                 .anyRequest().authenticated()
     *             )
     *             .formLogin(form -> form.disable())
     *             .httpBasic(basic -> basic.disable());
     *
     *         return http.build();
     *     }
     */

    //***************************************************


