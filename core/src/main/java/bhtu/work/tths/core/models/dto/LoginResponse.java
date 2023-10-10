package bhtu.work.tths.core.models.dto;

import java.util.List;

public record LoginResponse(
        String username,
        List<String> accesses,
        String authorization
) {
}
