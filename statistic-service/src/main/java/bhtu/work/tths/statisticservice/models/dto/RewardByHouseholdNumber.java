package bhtu.work.tths.statisticservice.models.dto;

import java.util.List;

import bhtu.work.tths.statisticservice.models.PrizeGroup;

public record RewardByHouseholdNumber(
        List<PrizeGroup> totalPrizeGroups,
        String householdNumber) {

}
