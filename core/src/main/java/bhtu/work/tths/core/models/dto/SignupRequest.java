package bhtu.work.tths.core.models.dto;

public record SignupRequest(
        String username,
        String password,
        String householdNumber
) {
}
