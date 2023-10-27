package bhtu.work.tths.statisticservice.utils;

import bhtu.work.tths.share.utils.counter.Countable;
import bhtu.work.tths.statisticservice.models.PrizeGroup;

import java.util.Objects;

public class PrizeWrapper implements Countable {
    private final PrizeGroup core;

    public PrizeWrapper(PrizeGroup group) {
        this.core = group;
    }

    @Override
    public Number getCount() {
        return core.getAmount();
    }

    @Override
    public void stack(Object countable) {
        core.setAmount(core.getAmount() + ((PrizeWrapper) countable).core.getAmount());
    }

    @Override
    public boolean sameType(Object countable) {
        if (this == countable) return false;
        if (countable == null) return false;
        if (countable instanceof PrizeWrapper countablePrize) {
            return Objects.equals(this.core.getNameOfPrize(), countablePrize.getCore().getNameOfPrize());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return core.getNameOfPrize().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.sameType(obj);
    }

    public PrizeGroup getCore() {
        return core;
    }
}
