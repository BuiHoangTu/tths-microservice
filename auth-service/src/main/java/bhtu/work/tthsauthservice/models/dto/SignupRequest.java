package bhtu.work.tthsauthservice.models.dto;

public record SignupRequest(
        String username,
        String password,
        String householdNumber
) {
}
