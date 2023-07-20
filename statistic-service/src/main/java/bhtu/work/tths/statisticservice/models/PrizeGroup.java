package bhtu.work.tths.statisticservice.models;

import lombok.Data;

/**
 * A price student got  
 * @param nameOfPrize keo(cai), banh(kg)
 * @param amount amount of this price that the student got 
 */ 
@Data
public class PrizeGroup {
    private String nameOfPrize;
    private int amount;

    public PrizeGroup(String nameOfPrize, int amount) {
        this.nameOfPrize = nameOfPrize;
        this.amount = amount;
    }

}
