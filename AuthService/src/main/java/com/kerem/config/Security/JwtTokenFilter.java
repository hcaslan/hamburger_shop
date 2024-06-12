package com.kerem.config.Security;

import com.kerem.service.AuthService;
import com.kerem.utility.JwtTokenManager;
import com.kerem.exceptions.AuthMicroServiceException;
import com.kerem.exceptions.ErrorType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;


@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenManager jwtTokenManager;
    private final AuthService authService;

    public JwtTokenFilter(JwtTokenManager jwtTokenManager, AuthService authService) {
        this.jwtTokenManager = jwtTokenManager;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //requestte gelen bearer tokenı yakalama
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null) {
            //çıktıya bakarak tokenın başındaki "Bearer " kısmını atmayı unutma:
            String token = bearerToken.substring(7);
            String authId = jwtTokenManager.getIdFromToken(token).orElseThrow(() -> new AuthMicroServiceException(ErrorType.INVALID_TOKEN));
            System.out.println("Token içindeki id: " + authId);

            UserDetails userDetails = authService.findById(authId);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
