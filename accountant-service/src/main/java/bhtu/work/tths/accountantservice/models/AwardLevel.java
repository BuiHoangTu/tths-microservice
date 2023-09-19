package bhtu.work.tths.accountantservice.models;

import lombok.Data;

import java.util.Objects;

/**
 * Amount of money for each achivement 
 */
@Data
public class AwardLevel {
    private String achievement;
    private int prizeValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AwardLevel that)) return false;
        return prizeValue == that.prizeValue && Objects.equals(achievement, that.achievement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(achievement);
    }
}