package technicaltest.api.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Utility Class for handling JWT in Spring Security.
 * This class provides methods for creating and validating the tokens
 */
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret_key;
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    @Value("${jwt.tokenDuration}")
    private static int TOKEN_DURATION;

    public JwtUtil() {

    }

    public String createToken(Authentication authentication, UUID uuid) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> authorityStrings = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        Claims claims = Jwts.claims()
                .setSubject(authentication.getName());
        claims.put("role", authorityStrings);
        claims.put("uuid", uuid);
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + JwtUtil.TOKEN_DURATION);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret_key)
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret_key)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public UUID getUuidFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret_key)
                .parseClaimsJws(token)
                .getBody();
        return UUID.fromString((String) claims.get("uuid"));
    }
//
//    public List<String> getRolesFromJwt(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(secret_key)
//                .parseClaimsJws(token)
//                .getBody();
//        return (List<String>) claims.get("role");
//    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}
