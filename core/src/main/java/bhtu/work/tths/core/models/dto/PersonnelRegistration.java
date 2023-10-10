package bhtu.work.tths.core.models.dto;



import bhtu.work.tths.core.models.enums.EUserAccess;

import java.util.Set;

public record PersonnelRegistration(
        String username,
        String password,
        Set<EUserAccess> accesses,
        String region
) {
}
