package bhtu.work.tthsstatisticservice.models.dto;

import java.util.List;

import bhtu.work.tthsstatisticservice.models.PrizeGroup;

public record RewardByHouseholdNumber(
        List<PrizeGroup> totalPrizeGroups,
        String householdNumber) {

}
