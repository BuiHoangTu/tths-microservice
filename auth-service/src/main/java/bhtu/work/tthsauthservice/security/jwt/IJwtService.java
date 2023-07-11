package bhtu.work.tthsauthservice.security.jwt;

import bhtu.work.tthsauthservice.security.services.MyUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.lang.NonNull;

public interface IJwtService {
    String getJwt(HttpServletRequest request);

    ResponseCookie generateJwtCookie(@NonNull MyUserDetails userPrincipal);

    ResponseCookie getCleanJwtCookie();

    String extractUserName(String jwt);

    boolean validateJwt(String authToken);
}
