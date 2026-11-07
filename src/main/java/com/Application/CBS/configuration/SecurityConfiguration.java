package com.bankcore.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final BasicAuthenticationEntryPoint basicAuthenticationEntryPoint;
    private final String adminUsername;
    private final String adminPassword;

    public SecurityConfiguration(
            BasicAuthenticationEntryPoint basicAuthenticationEntryPoint,
            @Value("${bankcore.security.username:bankcore-admin}") String adminUsername,
            @Value("${bankcore.security.password:change-me-in-env}") String adminPassword) {
        this.basicAuthenticationEntryPoint = basicAuthenticationEntryPoint;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/actuator/health").permitAll()
                        .requestMatchers(HttpMethod.POST, "/customer/v1/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/account/v1/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/transaction/v1/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/account/v1/**").authenticated()
                        .anyRequest().authenticated())
                .httpBasic(withDefaults())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(basicAuthenticationEntryPoint));
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername(adminUsername)
                .password(passwordEncoder().encode(adminPassword))
                .roles("ADMIN")
                .build());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}