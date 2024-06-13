package com.kerem.config.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
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
    public AccessDeniedHandler accessDeniedHandler() {
        AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
        accessDeniedHandler.setErrorPage("/accessDenied");
        return accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/swagger-ui/**","/v3/api-docs/**","/swagger-ui/**", "/webjars/**",ROOT+AUTH+REGISTER+"/**", ROOT+AUTH+ACTIVATEACCOUNT, ROOT+AUTH+LOGIN+"/**").permitAll()
                                .requestMatchers(ROOT+AUTH+GETRESETPASWORDCODE,
                                        ROOT+AUTH+RESETPASSWORD,
                                        ROOT+AUTH+CHANGEPASSWORD,
                                        ROOT+AUTH+DELETEMYACCOUNT).hasAnyAuthority("USER","ADMIN")
                                .requestMatchers(ROOT+AUTH+DELETE,
                                        ROOT+AUTH+FINDALL,
                                        ROOT+AUTH+FINDBYID).hasAuthority("ADMIN")
                                .anyRequest().authenticated())
                .exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler()))
                .csrf(csrf -> csrf.disable());
        return httpSecurity.build();
    }
}

