package bhtu.work.tths.core.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * How the prizes cost at dateOfApply
 * @param dateOfApply date when these prizes types cost 
 */
@Data
@Document
public class PrizePeriod {
    @Id
    private LocalDate dateOfApply;
    private List<PrizeValue> rewardTypes = new ArrayList<>();

}
