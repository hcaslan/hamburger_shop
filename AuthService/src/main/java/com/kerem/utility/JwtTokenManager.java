package com.kerem.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kerem.constant.ERole;
import com.kerem.exceptions.AuthMicroServiceException;
import com.kerem.exceptions.ErrorType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenManager {
    @Value("${authservice.secret.secret-key}")
    String secretKey;
    @Value("${authservice.secret.issuer}")
    String issuer;
    Long expireTime = 1000L * 60*15;

    public Optional<String> createToken(String id) {
        String token = "";
        try {
            token = JWT.create().withAudience()
                    .withClaim("id",
                            id)
                    .withClaim("whichpage",
                            "AuthMicroService")
                    .withClaim("ders",
                            "Java JWT")
                    .withClaim("grup",
                            "Java14")
                    .withIssuer(issuer)
                    .withIssuedAt(new Date()) //Tokenın yaratıldığı an.
                    .withExpiresAt(new Date(System.currentTimeMillis() + expireTime)) //Data,Instant
                    .sign(Algorithm.HMAC512(secretKey));
            return Optional.of(token);
        }
        catch (IllegalArgumentException e) {
            throw new AuthMicroServiceException(ErrorType.TOKEN_CREATION_FAILED);
        }
        catch (JWTCreationException e) {
            throw new AuthMicroServiceException(ErrorType.TOKEN_CREATION_FAILED);
        }
    }

    public Optional<String> createTokenWithIdAndRole(String id, ERole role) {
        String token = "";
        try {
            token = JWT.create().withAudience()
                    .withClaim("id",
                            id)
                    .withClaim("role",
                            role.toString())
                    .withClaim("whichpage",
                            "AuthMicroService")
                    .withClaim("ders",
                            "Java JWT")
                    .withClaim("grup",
                            "Java14")
                    .withIssuer(issuer)
                    .withIssuedAt(new Date()) //Tokenın yaratıldığı an.
                    .withExpiresAt(new Date(System.currentTimeMillis() + expireTime)) //Data,Instant
                    .sign(Algorithm.HMAC512(secretKey));
            return Optional.of(token);
        }
        catch (IllegalArgumentException e) {
            throw new AuthMicroServiceException(ErrorType.TOKEN_CREATION_FAILED);
        }
        catch (JWTCreationException e) {
            throw new AuthMicroServiceException(ErrorType.TOKEN_CREATION_FAILED);
        }
    }


    public Optional<String> getIdFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            if(decodedJWT==null)
                return Optional.empty();

            String id = decodedJWT.getClaim("id").asString();
            return Optional.of(id);
        }
        catch (IllegalArgumentException e) {
            throw new AuthMicroServiceException(ErrorType.INVALID_TOKEN_ARGUMENT);
        }
        catch (JWTVerificationException e) {
            throw new AuthMicroServiceException(ErrorType.TOKEN_VERIFICATION_FAILED);
        }
    }

    public Optional<ERole> getRoleFromToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            if(decodedJWT==null)
                return Optional.empty();

            String role1 = decodedJWT.getClaim("role").toString();
            System.out.println(role1);
            if (role1.equalsIgnoreCase("\"USER\"")){
                return Optional.of(ERole.USER);
            } else{
                return Optional.of(ERole.ADMIN);
            }
        }
        catch (IllegalArgumentException e) {
            throw new AuthMicroServiceException(ErrorType.INVALID_TOKEN_ARGUMENT);
        }
        catch (JWTVerificationException e) {
            throw new AuthMicroServiceException(ErrorType.TOKEN_VERIFICATION_FAILED);
        }
    }
}
