package com.gavinkim.security;

import com.gavinkim.util.Utils;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Component
public class TokenProvider {
    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    @Value("${jwt.token.secret}")
    private String tokenSecret;

    public String generateToken(Authentication authentication,LocalDateTime expiredAt){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getEmail())
                .setIssuedAt(Utils.LocalDateTimeToDate(LocalDateTime.now()))
                .setExpiration(Utils.LocalDateTimeToDate(expiredAt))
                .signWith(SIGNATURE_ALGORITHM, tokenSecret)
                .compact();
    }
    public String getUserEmailFromToken(String token){
        Claims claims = Jwts.parser().setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    public boolean validateToken(String accessToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(accessToken);
            if (claims.getBody().getExpiration().after(new Date())) {
                return true;
            }
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.error("Invalid JWT signature {} ",e.getMessage());
            throw new TokenException("Invalid Token");
        }
        return false;
    }
}
