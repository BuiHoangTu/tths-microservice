package bhtu.work.tths.core.models;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

import lombok.Data;

/**
 * Event student attended and got valid chievement 
 */
@Data
public class EventOfStudent {
    @Id
    private LocalDate dateOfEvent;
    private String nameOfEvent;
    private String achievement;
    private String classStr;
    /**
     * keo(cai), banh(goi)
     */
    private Set<PrizeGroup> prizes = new HashSet<>();
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

}