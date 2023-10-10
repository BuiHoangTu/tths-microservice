package bhtu.work.tths.core.models.dto;

import java.time.LocalDate;
import java.util.List;

import bhtu.work.tths.core.models.PrizeGroup;

public record RewardByEvent(
                List<PrizeGroup> allRewards,
                int totalExpense,
                LocalDate dateOfEvent,
                String nameOfEvent) {

}
