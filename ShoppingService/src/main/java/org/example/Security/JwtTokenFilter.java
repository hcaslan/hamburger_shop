package org.example.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.constant.Session;
import org.example.exceptions.ErrorType;
import org.example.exceptions.ShoppingServiceException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;


@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final RabbitTemplate rabbitTemplate;
    @Value("${authservice.secret.secret-key}")
    String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                DecodedJWT jwt = JWT.require(Algorithm.HMAC512(secretKey))
                        .build()
                        .verify(token);
                String role = "ROLE_" + jwt.getClaim("role").asString();
                String authId = jwt.getClaim("id").asString();
                String profileId = (String) rabbitTemplate.convertSendAndReceive("exchange.direct","getProfileId.Route", authId);
                Session.setSession(token, authId,profileId);
                System.out.println("profileId: " + profileId);
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(null, null, Collections.singletonList(authority));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw new ShoppingServiceException(ErrorType.INVALID_TOKEN);
            }
        }
        filterChain.doFilter(request, response);
    }
}
