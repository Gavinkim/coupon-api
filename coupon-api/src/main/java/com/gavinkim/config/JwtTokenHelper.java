package com.gavinkim.config;

import com.gavinkim.util.Utils;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenHelper {
    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
    @Value("${jwt.token.secret}")
    private String tokenSecret;

    public String generateToken(String email, LocalDateTime expireyDate){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Utils.LocalDateTimeToDate(LocalDateTime.now()))
                .setExpiration(Utils.LocalDateTimeToDate(expireyDate))
                .signWith(SIGNATURE_ALGORITHM, tokenSecret)
                .compact();
    }

    public boolean validateToken(String accessToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(accessToken);
            if (claims.getBody().getExpiration().after(new Date())) {
                return true;
            }
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

}
