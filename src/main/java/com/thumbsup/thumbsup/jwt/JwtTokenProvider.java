package com.thumbsup.thumbsup.jwt;

import com.thumbsup.thumbsup.entity.CustomUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    private final String JWT_SECRET = "LondonIsBlue";

    public String generateToken(CustomUserDetails userDetails, Long jwtExpiration) {
        Date now = new Date();
        String subject;
        Date expiryDate = new Date(now.getTime() + jwtExpiration);
        //Tạo subject cho JWT
        subject = userDetails.getUsername();
        //Tạo claims cho JWT
        Claims claims = Jwts.claims();
        claims.put("role", userDetails.getRole()); // Thêm vai trò vào payload
        // Tạo chuỗi json web token từ user name của user.
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(now).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
    }

    public String getUserNameFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String getRoleFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
        return (String) claims.get("role");
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid Token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        } catch (SignatureException ex) {
            log.error("Signature invalid");
        }
        return false;
    }
}
