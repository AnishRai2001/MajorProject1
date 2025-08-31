package com.EmployeeService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.EmployeeService.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Public endpoints (no JWT required)
            		  .requestMatchers(
            			        "/api/auth/register",
            			        "/api/auth/login",
            			        "/api/auth/verify",
            			        "/api/auth/forgot-password",
            			        "/api/auth/reset-password"
            			    ).permitAll()

                // Role-based endpoints
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/manager/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN", "MANAGER")

                // Any other request must be authenticated
                .anyRequest().authenticated()
            )
            .authenticationProvider(authProvider())
            .addFilterBefore(jwtFilter,
                    org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
