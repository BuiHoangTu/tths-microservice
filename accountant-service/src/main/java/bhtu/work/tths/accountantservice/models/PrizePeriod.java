package bhtu.work.tths.accountantservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * How the prizes cost at dateOfApply
 * @param dateOfApply date when these prizes types cost 
 */
@Document
public class PrizePeriod {
    @Id
    private LocalDate dateOfApply;
    private List<PrizeValue> rewardTypes = new ArrayList<>();

    public LocalDate getDateOfApply() {
        return dateOfApply;
    }

    public void setDateOfApply(LocalDate dateOfApply) {
        this.dateOfApply = dateOfApply;
    }

    public List<PrizeValue> getRewardTypes() {
        return rewardTypes;
    }

    public void setRewardTypes(List<PrizeValue> rewardTypes) {
        this.rewardTypes = rewardTypes;
    }

}
