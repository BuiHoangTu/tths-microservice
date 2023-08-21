package bhtu.work.tths.authservice.security.jwt;

import bhtu.work.tths.authservice.security.services.MyUserDetails;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtils implements IJwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private static String AUTHORITIES = "authorities";

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${spring.app.jwtCookieName}")
    private String jwtCookieName;

    @Override
    public String getJwt(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    @Override
    public ResponseCookie generateJwtCookie(@NonNull MyUserDetails userPrincipal) {
        String jwt = generateToken(userPrincipal);
        return ResponseCookie.from(jwtCookieName, jwt).path("/api").maxAge(24 * 60 * 60).httpOnly(true).build();
    }

    @Override
    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie.from(jwtCookieName, "").path("/api").build();
    }

    @Override
    public String extractUserName(String jwt) {
        return this.extractClaim(jwt, Claims::getSubject);
    }

    @Override
    public List<String> extractAuthorities(String jwt) {
        var authorities = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwt)
                .getBody()
                .get(AUTHORITIES);
        if (authorities instanceof List<?>) {
            return ((List<?>) authorities).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
        else return Collections.emptyList();
    }

    @Override
    public boolean validateJwt(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    private String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .claim(AUTHORITIES, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimApplier) {
        final Claims c = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody()/*.getSubject()*/;
        return claimApplier.apply(c);
    }

}
