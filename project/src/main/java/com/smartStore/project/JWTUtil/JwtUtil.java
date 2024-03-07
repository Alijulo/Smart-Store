package com.smartStore.project.JWTUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;


import org.springframework.security.core.Authentication;  // Added for isTokenValid method


@Service
public class JwtUtil {
    public String generateToken(UserDetails userDetails){
        String rolesString = String.join(",", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return Jwts.builder().setSubject(userDetails.getUsername())
                .claim("role", rolesString) // add role as a custom claim
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Generates a refresh token with additional claims and UserDetails.
    public String generateRefreshToken(Map<String, Object> extraClaim, UserDetails userDetails){
        return Jwts.builder().setClaims(extraClaim)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    // Extracts the username from a given JWT token.
    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    // Helper method to extract a specific claim from the token using a ClaimsResolver function.
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers){
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    // Generates the signing key used for JWT.
    private Key getSignInKey(){
        byte[] key = Decoders.BASE64.decode("a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2");
        return Keys.hmacShaKeyFor(key);
    }

    // Extracts all claims (payload) from the token using the signing key.
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    //Extract role
    public String extractRole(String token){
        return extractClaim(token, claims -> claims.get("role", String.class));
    }


    // Checks if the given JWT token is still valid for the provided UserDetails.
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Checks if the expiration date of the token is before the current date, indicating token expiration.
    private boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

}
