package bhtu.work.tths.statisticservice.models;

import bhtu.work.tths.share.utils.counter.Countable;
import bhtu.work.tths.share.utils.counter.StackCounter;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * Event student attended and got valid chievement 
 */
@Data
public class EventOfStudent implements Countable {
    @Id
    private LocalDate dateOfEvent;
    private String nameOfEvent;
    private String achievement;
    private String classStr;
    /**
     * keo(cai), banh(goi)
     */
    private Set<PrizeGroup> prizes = new StackCounter<>();
    private int totalExpense;

    public EventOfStudent(LocalDate dateOfEvent, String nameOfEvent, String achievement, String classStr,
                          Collection<PrizeGroup> prizes, int totalExpense) {
        this.dateOfEvent = dateOfEvent;
        this.nameOfEvent = nameOfEvent;
        this.achievement = achievement;
        this.classStr = classStr;
        this.prizes.addAll(prizes);
        this.totalExpense = totalExpense;
    }

    public EventOfStudent() {
    }

    @Override
    public Number getCount() {
        return totalExpense;
    }

    @Override
    public void stack(Object countableObj) {
        var countable = (EventOfStudent) countableObj;
        // stack the expense
        this.setTotalExpense(this.getCount().intValue() + countable.getCount().intValue());
        // stack the content
        this.prizes.addAll(countable.prizes);
    }

    @Override
    public boolean sameType(Object countable) {
        if (this == countable) return false;
        if (countable == null) return false;
        if (countable instanceof EventOfStudent countablePrize) {
            return Objects.equals(this.getDateOfEvent(), countablePrize.getDateOfEvent());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.getDateOfEvent().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.sameType(obj);
    }
}