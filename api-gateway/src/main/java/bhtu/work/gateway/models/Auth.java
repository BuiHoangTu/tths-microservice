package bhtu.work.gateway.models;

import java.util.List;

public record Auth (
        boolean isAuthenticated,
        List<String> authorities
){}
