package com.alkemy.ong.security.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Herramienta validadora de un token JWT.
 * 
 * @author Tomás Sánchez
 * @since 1.0
 */
public class TokenValidator {

    private static final String SECRET = "n8*<*H^gUdSa@?>R_";
    private static final String TOKEN_BREARER_PREFIX = "Bearer ";
    private Algorithm algorithm;
    private JWTVerifier verifier;

    public TokenValidator() {
        algorithm = Algorithm.HMAC256(SECRET.getBytes());
        verifier = JWT.require(algorithm).build();
    }

    /**
     * Obtains an User Authentication Token from a header
     * 
     * @param authHeader the authentication header
     * @return the user-password-authentication token object.
     */
    public UsernamePasswordAuthenticationToken retrieveUserAuthTokenFromHeader(String authHeader) {
        return obtainUserAuthToken(decodeToken(obtainTokenFromHeader(authHeader)));
    }

    /**
     * Generates an authentication token for an User.
     * 
     * @param user from springboot userdetails
     * @param expirationInMinutes the expiration time in minutes
     * @param issuer the issuer value
     * @param withClaims wheter to sign with claims
     * @return a generated token
     */
    public String generateTokenForUser(User user, int expirationInMinutes, String issuer,
            boolean withClaims) {

        Builder jwtBuilder = JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationInMinutes))
                .withIssuer(issuer);

        return withClaims ? jwtBuilder.withClaim("roles", obtainAuthorities(user)).sign(algorithm)
                : jwtBuilder.sign(algorithm);
    }

    /**
     * Obtains a tokeken from the authentication header
     * 
     * @param authHeader the authentication header
     * @return the token withouth the prefix 'BEARER '
     */
    private String obtainTokenFromHeader(String authHeader) {
        return authHeader.substring(TOKEN_BREARER_PREFIX.length());
    }

    /**
     * Verifies a token.
     * 
     * @param token to be verified by the validator Algorithm
     * @return a Decoded Jason Web Token
     */
    private DecodedJWT decodeToken(String token) {
        return verifier.verify(token);
    }


    /**
     * Obtains the user auth token from a decoded Jason Web Token.
     * 
     * @param decodedJwt a decoded JSON web Token
     * @return
     */
    private static UsernamePasswordAuthenticationToken obtainUserAuthToken(DecodedJWT decodedJwt) {

        String username = decodedJwt.getSubject();
        String[] roles = decodedJwt.getClaim("roles").asArray(String.class);

        return new UsernamePasswordAuthenticationToken(username, null, listAuthorities(roles));
    }

    /**
     * List all authorities decoded.
     * 
     * @param roles decoded roles.
     * @return a collection of Granted Authorities
     */
    private static Collection<SimpleGrantedAuthority> listAuthorities(String[] roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        return authorities;
    }

    /**
     * Obtains an user authorities list.
     * 
     * @param user to authorize
     * @return a list of authorities.
     */
    private List<String> obtainAuthorities(User user) {
        return user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
