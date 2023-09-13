package bhtu.work.tths.authservice.models.dto;

public record LoginRequest(
        String username,
        String password
) {
}
