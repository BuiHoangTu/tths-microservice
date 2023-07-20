package bhtu.work.tths.studentservice.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private List<PrizeGroup> prizes = new ArrayList<>();
    private int totalExpense;

    public EventOfStudent(LocalDate dateOfEvent, String nameOfEvent, String achievement, String classStr,
            List<PrizeGroup> prizes, int totalExpense) {
        this.dateOfEvent = dateOfEvent;
        this.nameOfEvent = nameOfEvent;
        this.achievement = achievement;
        this.classStr = classStr;
        this.prizes = prizes;
        this.totalExpense = totalExpense;
    }

    public EventOfStudent() {
    }

}