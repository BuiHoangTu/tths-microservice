package bhtu.work.tthsauthservice.models.dto;

public record LoginRequest(
        String username,
        String password
) {
}