package bhtu.work.tths.statisticservice.utils;

import bhtu.work.tths.share.utils.counter.Counter;
import bhtu.work.tths.statisticservice.models.EventOfStudent;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Count total expense of each event
 */
// Todo: add Prize counter
public class EventCounter implements Counter<EventOfStudent> {
    private final Map<LocalDate, EventOfStudent> counterMap = new HashMap<>();


    @Override
    public long put(EventOfStudent unit) {
        if (unit == null) throw new NullPointerException("Can't insert null");

        var old = counterMap.get(unit.getDateOfEvent());

        if (old != null){
            var expense = old.getTotalExpense() + unit.getTotalExpense();
            old.setTotalExpense(expense);
            return expense;
        } else {
            counterMap.put(unit.getDateOfEvent(), unit);
            return unit.getTotalExpense();
        }
    }

    @Override
    public long getCount(EventOfStudent unit) {
        unit = this.counterMap.get(unit.getDateOfEvent());
        if (unit != null) return unit.getTotalExpense();
        else return 0;
    }

    @Override
    public void forEach(BiConsumer<? super EventOfStudent, ? super Number> action) {
        this.counterMap.forEach((_k, v) -> action.accept(v, v.getTotalExpense()));
    }
}
