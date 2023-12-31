package bhtu.work.tths.accountantservice.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * How to award for each achievement at dateOfApply
 */
@Document
public class AwardPeriod {
    @Id
    private LocalDate dateOfApply;
    private List<AwardLevel> awardLevels = new ArrayList<>();

    public LocalDate getDateOfApply() {
        return dateOfApply;
    }

    public void setDateOfApply(LocalDate dateOfApply) {
        this.dateOfApply = dateOfApply;
    }

    public List<AwardLevel> getAwardLevels() {
        return awardLevels;
    }

    public void setAwardLevels(List<AwardLevel> awardLevels) {
        this.awardLevels = awardLevels;
    }


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
