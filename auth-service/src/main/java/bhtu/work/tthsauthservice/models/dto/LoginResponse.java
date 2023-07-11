package bhtu.work.tthsauthservice.models.dto;

import java.util.List;

public record LoginResponse(
        String username,
        List<String> accesses
) {
}
