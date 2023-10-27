package bhtu.work.tths.statisticservice.models;

import bhtu.work.tths.share.utils.counter.Countable;
import lombok.Data;

import java.util.Objects;

/**
 * A price student got  
 * @param nameOfPrize keo(cai), banh(kg)
 * @param amount amount of this price that the student got 
 */ 
@Data
public class PrizeGroup implements Countable {
    private String nameOfPrize;
    private int amount;

    public PrizeGroup(String nameOfPrize, int amount) {
        this.nameOfPrize = nameOfPrize;
        this.amount = amount;
    }

    @Override
    public Number getCount() {
        return getAmount();
    }

    @Override
    public void stack(Object countable) {
        this.setAmount(this.getAmount() + ((PrizeGroup) countable).getAmount());
    }

    @Override
    public boolean sameType(Object countable) {
        if (this == countable) return false;
        if (countable == null) return false;
        if (countable instanceof PrizeGroup countablePrize) {
            return Objects.equals(this.getNameOfPrize(), countablePrize.getNameOfPrize());
        } else {
            return false;
        }
    }

    // define hash code so Counter can work efficiently
    @Override
    public int hashCode() {
        return this.getNameOfPrize().hashCode();
    }

    // equals decide the working process of counter
    @Override
    public boolean equals(Object obj) {
        return this.sameType(obj);
    }

}
