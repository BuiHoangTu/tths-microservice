package bhtu.work.tths.statisticservice.utils;

import bhtu.work.tths.share.utils.counter.ComplexCounter;
import bhtu.work.tths.share.utils.counter.Countable;
import bhtu.work.tths.share.utils.counter.Counter;
import bhtu.work.tths.statisticservice.models.EventOfStudent;
import bhtu.work.tths.statisticservice.models.PrizeGroup;

import java.time.LocalDate;
import java.util.function.BiConsumer;

/**
 * Count total expense of each event
 */
// Todo: add Prize counter
public class EventCounter implements Counter<EventOfStudent> {
    private final Counter<Countable<LocalDate>> counter = new ComplexCounter<>();

    private static Countable<LocalDate> warp(EventOfStudent event) {
        return Countable.of(event.getDateOfEvent(), event, event.getTotalExpense());

    }

    @Override
    public long put(EventOfStudent unit) {
        return this.counter.put(Countable.of(unit.getDateOfEvent(), unit, unit.getTotalExpense()));
    }

    @Override
    public long getCount(EventOfStudent unit) {
        return this.counter.getCount(Countable.asKey(unit.getDateOfEvent()));
    }

    @Override
    public void forEach(BiConsumer<? super EventOfStudent, ? super Number> action) {
        this.counter.forEach((c, _v) -> {
            action.accept((EventOfStudent) c.get(), c.getCount());
        });
    }

}
