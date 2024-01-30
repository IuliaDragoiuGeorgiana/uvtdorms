package com.uvtdorms.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.uvtdorms.repository.dto.UserDto;
import com.uvtdorms.repository.entity.enums.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {
    @Value("${security.jwt.token.secret-key:secret-key")
    private String secretKey;

    @PostConstruct
    protected void init()
    {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDto dto)
    {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3_600_000);

        return JWT.create()
                .withIssuer(dto.getEmail())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("role", dto.getRole().toString())
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token)
    {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier =  JWT.require(algorithm).build();

        DecodedJWT decoded = verifier.verify(token);

        UserDto user = UserDto.builder()
                .email(decoded.getIssuer())
                .role(Role.valueOf(decoded.getClaim("role").asString()))
                .build();

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
}
