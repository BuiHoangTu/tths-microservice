package bhtu.work.tths.authservice.models.dto;


import bhtu.work.tths.share.models.enums.EUserAccess;

import java.util.Set;

public record PersonnelRegistration(
        String username,
        String password,
        Set<EUserAccess> accesses,
        String region
) {
}
