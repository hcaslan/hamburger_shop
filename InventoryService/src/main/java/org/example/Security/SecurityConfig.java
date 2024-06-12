package org.example.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.example.constant.EndPoints.*;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll()
                                .requestMatchers(ROOT+URUN+SAVE,ROOT+URUN+UPDATE,ROOT+URUN+DELETE+"/**").hasRole("ADMIN")
                                .requestMatchers(ROOT+URUN+FINDALL,ROOT+URUN+FINDBYID+"/**").hasAnyRole("USER","ADMIN")
                                .requestMatchers(ROOT+CART+GETCARTBYUSERID,ROOT+CART+ADDITEMTOCART,ROOT+CART+REMOVEITEMFROMCART,ROOT+CART+CLEARCART,ROOT+CART+CHECKOUT).hasAnyRole("USER","ADMIN")
                                .anyRequest().authenticated())
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sessionManagement -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .csrf(csrf -> csrf.disable());
        return httpSecurity.build();
    }

}

