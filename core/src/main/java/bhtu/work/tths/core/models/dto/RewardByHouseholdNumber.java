package bhtu.work.tths.core.models.dto;

import java.util.List;

import bhtu.work.tths.core.models.PrizeGroup;

public record RewardByHouseholdNumber(
        List<PrizeGroup> totalPrizeGroups,
        String householdNumber) {

}
