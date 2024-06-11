package org.example.Security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.enums.ERole;
import org.example.exceptions.ErrorType;
import org.example.exceptions.InverntoryServiceException;
import org.example.utility.JwtTokenManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenManager jwtTokenManager;


    public JwtTokenFilter(JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //requestte gelen bearer tokenı yakalama
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null) {
            //çıktıya bakarak tokenın başındaki "Bearer " kısmını atmayı unutma:
            String token = bearerToken.substring(7);
            ERole role = jwtTokenManager.getRoleFromToken(token).orElseThrow(() -> new InverntoryServiceException(ErrorType.INVALID_TOKEN));
            System.out.println("Token içindeki role: " + role);

            UserDetails userDetails = User.builder().username("").password("").authorities(new SimpleGrantedAuthority(role.toString())).build();
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
