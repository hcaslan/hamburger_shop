package org.example.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.example.constant.EndPoints.*;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll()
                                .requestMatchers(URUN+"/**").hasRole("ADMIN")
                                .requestMatchers(URUN+FINDALL,URUN+FINDBYID).hasRole("USER")
                                .anyRequest().authenticated())
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable());
        return httpSecurity.build();
    }

}

