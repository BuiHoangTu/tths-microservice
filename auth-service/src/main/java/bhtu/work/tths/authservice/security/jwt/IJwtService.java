package bhtu.work.tths.authservice.security.jwt;

import bhtu.work.tths.authservice.security.services.MyUserDetails;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.lang.NonNull;

import java.util.List;

public interface IJwtService {
    String getJwt(HttpServletRequest request);

    ResponseCookie generateJwtCookie(@NonNull MyUserDetails userPrincipal);

    ResponseCookie getCleanJwtCookie();

    String extractUserName(String jwt);

    List<String> extractAuthorities(String jwt);

    boolean validateJwt(String authToken);
}
