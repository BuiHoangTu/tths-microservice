package bhtu.work.tths.authservice.models.dto;

public record SignupRequest(
        String username,
        String password,
        String householdNumber
) {
}
