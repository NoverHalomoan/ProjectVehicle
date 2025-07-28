package com.bengkel.backendBengkel.base.security;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bengkel.backendBengkel.base.untilize.EnviromentGetData;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtilFilter {

    private final EnviromentGetData enviromentGetData;

    private Key key = null;

    public JwtUtilFilter(EnviromentGetData enviromentGetData) {
        this.enviromentGetData = enviromentGetData;
    }

    private Key getKey() {
        if (key == null) {
            return Keys.hmacShaKeyFor(this.enviromentGetData.getJwt_secrete().getBytes());
        }
        return this.key;
    }

    private Key getKeyActivation() {
        return Keys.hmacShaKeyFor(this.enviromentGetData.getJwt_activationProfile().getBytes());
    }

    private Key getKeyUpdateProfile() {
        return Keys.hmacShaKeyFor(this.enviromentGetData.getJwt_activeUpdateProfile().getBytes());
    }

    public String generatedToken(String idUser, List<String> role) {
        return Jwts.builder()
                .setSubject(idUser)
                .claim("roles", role)
                .setIssuer(enviromentGetData.getIssuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 36000000))
                .signWith(getKey(), SignatureAlgorithm.HS256).compact();

    }

    public String generatedTokenActivation(String email) {
        return Jwts.builder().setSubject(email)
                .claim("type", "activation")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .signWith(getKeyActivation(), SignatureAlgorithm.HS256).compact();
    }

    public String generateTokenReactiveAccount(String email) {
        return Jwts.builder().setSubject(email)
                .claim("type", "updateProfile")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 100))
                .signWith(getKeyUpdateProfile(), SignatureAlgorithm.HS256).compact();
    }

    //this is for extraction data for user
    public String extractUsername(String token, String types) {
        String objectID = "";
        if (types.equals("login")) {
            objectID = Jwts.parserBuilder().setSigningKey(getKey())
                    .build().parseClaimsJws(token).getBody().getSubject();
        }
        if (types.equals("activation")) {
            objectID = Jwts.parserBuilder().setSigningKey(getKeyActivation())
                    .build().parseClaimsJws(token).getBody().getSubject();
        }
        if (types.equals("updateProfile")) {
            objectID = Jwts.parserBuilder().setSigningKey(getKeyUpdateProfile())
                    .build().parseClaimsJws(token).getBody().getSubject();
        }
        return objectID;

    }

    //this is for validation token
    public boolean validateToken(String idUser, String token) {
        //get token
        String ReturnExtract = extractUsername(token, "login");
        return ReturnExtract.equals(idUser) && !isTokenExpired(token, "login");
    }

    public Map<String, Boolean> validationActivationUser(String token) {
        //activation
        String extractValidation = extractUsername(token, "activation");
        Boolean validation = !isTokenExpired(token, "activation");
        return Map.of(extractValidation, validation);

    }

    public Map<String, Boolean> validationUpdateProfile(String token) {
        String extractValidation = extractUsername(token, "updateProfile");
        Boolean validation = !isTokenExpired(token, "updateProfile");
        return Map.of(extractValidation, validation);
    }

    //this is for check token expired
    public boolean isTokenExpired(String token, String types) {
        Date expiration = new Date();
        if (types.equals("login")) {
            expiration = Jwts.parserBuilder().setSigningKey(getKey())
                    .build().parseClaimsJws(token).getBody().getExpiration();
        }
        if (types.equals("activation")) {
            expiration = Jwts.parserBuilder().setSigningKey(getKeyActivation())
                    .build().parseClaimsJws(token).getBody().getExpiration();
        }
        if (types.equals("updateProfile")) {
            expiration = Jwts.parserBuilder().setSigningKey(getKeyUpdateProfile())
                    .build().parseClaimsJws(token).getBody().getExpiration();
        }
        return expiration.before(new Date());
    }

}
