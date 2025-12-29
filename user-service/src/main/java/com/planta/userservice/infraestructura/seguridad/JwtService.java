package com.planta.userservice.infraestructura.seguridad;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private static final long EXPIRATION = 1000 * 60 * 60;

    public String generarToken(Long userId, String correo, String rol) {

        return Jwts.builder()
                .setSubject(correo)
                .claim("id", userId)
                .claim("rol", rol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    /* ===================== OBTENER CLAIMS ===================== */
    public Claims obtenerClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /* ===================== VALIDAR TOKEN ===================== */
    public boolean esTokenValido(String token) {
        try {
            obtenerClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /* ===================== EXTRAER DATOS ===================== */
    public String obtenerCorreo(String token) {
        return obtenerClaims(token).getSubject();
    }

    public Long obtenerUserId(String token) {
        return obtenerClaims(token).get("id", Long.class);
    }

    public String obtenerRol(String token) {
        return obtenerClaims(token).get("rol", String.class);
    }
}
