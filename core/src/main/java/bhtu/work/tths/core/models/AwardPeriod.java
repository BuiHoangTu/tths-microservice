package bhtu.work.tths.core.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * How to award for each achievement at dateOfApply
 */
@Data
@Document
public class AwardPeriod {
    @Id
    private LocalDate dateOfApply;
    private List<AwardLevel> awardLevels = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AwardPeriod that)) return false;
        return Objects.equals(this.dateOfApply, that.dateOfApply) && Objects.equals(this.awardLevels, that.awardLevels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateOfApply);
    }
}
