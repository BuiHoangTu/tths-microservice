package bhtu.work.tthsauthservice.models.dto;

import bhtu.work.tthsauthservice.models.enums.EUserAccess;

import java.util.Set;

public record PersonnelRegistration(
        String username,
        String password,
        Set<EUserAccess> accesses,
        String region
) {
}
