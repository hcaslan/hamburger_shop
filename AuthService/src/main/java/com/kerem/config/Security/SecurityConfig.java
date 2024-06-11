package com.kerem.config.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.kerem.constant.EndPoints.*;
import static com.kerem.constant.EndPoints.AUTH;
import static com.kerem.constant.EndPoints.REGISTER;


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
                                .requestMatchers(AUTH+REGISTER+"/**", AUTH+ACTIVATEACCOUNT, AUTH+LOGIN+"/**").permitAll()
                                .requestMatchers(AUTH+GETRESETPASWORDCODE).hasAuthority("USER")
                                .requestMatchers(AUTH+RESETPASSWORD,
                                        AUTH+CHANGEPASSWORD,
                                        AUTH+DELETEMYACCOUNT).hasAuthority("USER")
                                .requestMatchers(AUTH+DELETE,
                                        AUTH+FINDALL,
                                        AUTH+FINDBYID).hasAuthority("ADMIN")
                                .anyRequest().authenticated())
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable());
        return httpSecurity.build();
    }

}

