package bhtu.work.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    public static class Config {

    }


    private final RouteValidator routeValidator;

    @Autowired
    public AuthFilter(RouteValidator routeValidator) {
        super(Config.class);
        this.routeValidator = routeValidator;
    }

    @Override
    public GatewayFilter apply(Config config) throws NullPointerException {
        return ((exchange, chain) -> {
            // if path need logged in
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            }

            return chain.filter(exchange);
        });
    }
}
