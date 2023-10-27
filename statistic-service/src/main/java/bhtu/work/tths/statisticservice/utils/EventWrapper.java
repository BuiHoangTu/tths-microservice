package bhtu.work.tths.statisticservice.utils;

import bhtu.work.tths.share.utils.counter.Countable;
import bhtu.work.tths.statisticservice.models.EventOfStudent;
import java.util.Objects;

public class EventWrapper implements Countable {
    private final EventOfStudent core;

    public EventWrapper(EventOfStudent x) {
        this.core = x;
    }

    @Override
    public Number getCount() {
        return core.getTotalExpense();
    }

    @Override
    public void stack(Object countableObj) {
        var countable = (Countable) countableObj;
        this.core.setTotalExpense(this.getCount().intValue() + countable.getCount().intValue());
    }


    @Override
    public boolean sameType(Object countable) {
        if (this == countable) return false;
        if (countable == null) return false;
        if (countable instanceof EventWrapper countablePrize) {
            return Objects.equals(this.core.getDateOfEvent(), countablePrize.getCore().getDateOfEvent());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return core.getDateOfEvent().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.sameType(obj);
    }

    public EventOfStudent getCore() {
        return core;
    }
}
