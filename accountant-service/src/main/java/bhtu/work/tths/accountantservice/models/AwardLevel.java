package bhtu.work.tths.accountantservice.models;

import lombok.Data;

/**
 * Amount of money for each achivement 
 */
@Data
public class AwardLevel {
    private String achievement;
    private int prizeValue;
}