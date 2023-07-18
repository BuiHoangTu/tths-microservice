package bhtu.work.gateway.security;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    private static final String[] openUrls = {
            "/api/open/**",
            "/api/auth/login",
            "/api/auth/signup",
            "/eureka/**"
    };
    private final AntPathMatcher matcher = new AntPathMatcher();
    /**
     * Check if path need validation
     */
    public Predicate<ServerHttpRequest> isSecured = ((serverHttpRequest) -> Arrays.stream(openUrls).noneMatch((uri) -> matcher.match(serverHttpRequest.getURI().getPath(),uri)));
}
