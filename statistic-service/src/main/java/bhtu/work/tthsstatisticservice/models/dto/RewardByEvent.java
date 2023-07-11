package bhtu.work.tthsstatisticservice.models.dto;

import java.time.LocalDate;
import java.util.List;

import bhtu.work.tthsstatisticservice.models.PrizeGroup;

public record RewardByEvent(
                List<PrizeGroup> allRewards,
                int totalExpense,
                LocalDate dateOfEvent,
                String nameOfEvent) {

}
