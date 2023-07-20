package bhtu.work.tths.authservice.models.dto;

import java.util.List;

public record LoginResponse(
        String username,
        List<String> accesses
) {
}
