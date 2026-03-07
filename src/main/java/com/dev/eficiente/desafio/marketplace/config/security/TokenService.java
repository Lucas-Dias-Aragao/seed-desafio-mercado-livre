package com.dev.eficiente.desafio.marketplace.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenService {

    //TODO adicionado para testes, será melhorado
    private final String SECRET = "minha-chave-super-secreta-super-secreta";

    private Date dataExpiracao() {
        Date agora = new Date();
        return new Date(agora.getTime() + 3600000);
    }

    public String gerarToken(UserDetails usuario) {

        return Jwts.builder()
                .subject(usuario.getUsername())
                .issuedAt(new Date())
                .expiration(dataExpiracao())
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .compact();
    }

    public String getSubject(String token) {

        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();

    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                    .build()
                    .parse(token);
            return true;
        } catch ( Exception e ) {
            return false;
        }
    }
}
