package bhtu.work.gateway.security;

import bhtu.work.gateway.models.Auth;
import bhtu.work.gateway.services.grpc.client.Authorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    public static class Config {

    }


    private final RouteValidator routeValidator;
    private final Authorize authorize;

    @Autowired
    public AuthFilter(RouteValidator routeValidator, Authorize authorize) {
        super(Config.class);
        this.routeValidator = routeValidator;
        this.authorize = authorize;
    }

    @Override
    public GatewayFilter apply(Config config) throws NullPointerException, IndexOutOfBoundsException {
        return ((exchange, chain) -> {
            // if path need logged in
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                String jwt = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
                if (jwt != null && jwt.startsWith("Bearer "))
                    jwt = jwt.substring(7); // remove Bearer
                Auth auth = authorize.getAuthorities(jwt);

                if (auth.isAuthenticated()) {
                    exchange.getRequest()
                            .mutate()
                            //.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                            .header("Accesses", auth.authorities().toArray(new String[0]));
                } else {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            }

            return chain.filter(exchange);
        });
    }
}
