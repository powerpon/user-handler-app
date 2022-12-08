package com.example.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.model.AppRole;
import com.example.demo.model.AppUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class JwtUtil {

    private JwtUtil(){}

    private static final Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
    private static final JWTVerifier verifier = JWT.require(algorithm).build();

    public static DecodedJWT getDecoderForRefreshToken(String refreshToken){
        return verifier.verify(refreshToken);
    }

    public static String getDecodedUsernameFromRefreshToken(String refreshToken){
        return getDecoderForRefreshToken(refreshToken).getSubject();
    }

    public static List<String> getListOfRolesFromJwtPayload(String refreshToken){
        return getDecoderForRefreshToken(refreshToken).getClaim("roles").asList(String.class);
    }

    public static String getRefreshTokenFromBearer(String authorHeader){
        return authorHeader.substring("Bearer ".length());
    }

    public static String refreshAppUserAccessToken(AppUser user, String issuerUrl){
        return JWT
                .create()
                .withSubject(user.getIdentity().getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                .withIssuer(issuerUrl)
                .withClaim("roles", List.of(user.getIdentity().getRoles().stream().map(AppRole::getName).collect(Collectors.toList())))
                .sign(Algorithm.HMAC256("secret".getBytes()));
    }

    public static Map<String, String> getMappedTokens(String accessToken, String refreshToken){
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }

    public static String generateAccessToken(User user, String issuerUrl){
        return JWT
                .create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                .withIssuer(issuerUrl)
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public static String generateRefreshToken(User user, String issuerUrl){
        return JWT
                .create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 90L * 24 * 60 * 1000))
                .withIssuer(issuerUrl)
                .sign(algorithm);
    }

}
