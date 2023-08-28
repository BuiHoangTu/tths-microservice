package bhtu.work.tths.statisticservice.utils;

import bhtu.work.tths.share.utils.counter.Countable;
import bhtu.work.tths.share.utils.counter.Counter;
import bhtu.work.tths.statisticservice.models.EventOfStudent;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Count total expense of each event
 */
public class EventCounter implements Counter<EventOfStudent> {
    // wrapper for EOS, add counter for Prize
    private static class CountableEvent implements Countable<LocalDate, EventOfStudent> {
        private final EventOfStudent event;
        // supporting counter for PG
        public final PrizeCounter prizeCounter = new PrizeCounter();

        public CountableEvent (EventOfStudent e) {
            event = e;
            // put prizes into counter
            prizeCounter.putAll(e.getPrizes());
        }
        @Override
        public LocalDate getKey() {
            return event.getDateOfEvent();
        }

        @Override
        public long getCount() {
            return event.getTotalExpense();
        }

        @Override
        public void setCount(long newCount) {
            event.setTotalExpense((int) newCount);
        }

        @Override
        public EventOfStudent get() {
            // upon get, set prizes as what's in the counter
            event.setPrizes(prizeCounter.values());
            return event;
        }
    }


    private final Map<LocalDate, CountableEvent> counterMap = new HashMap<>();


    @Override
    public long put(EventOfStudent unit) {
        if (unit == null) throw new NullPointerException("Can't insert null");
        var old = counterMap.get(unit.getDateOfEvent());

        // if this thing existed, increase count and put value in counter
        if (old != null) {
            var expense = old.getCount() + unit.getTotalExpense();
            old.setCount(expense);
            old.prizeCounter.putAll(unit.getPrizes());
            return expense;
        } else {
            // if not, just put it in
            var current = new CountableEvent(unit);
            counterMap.put(unit.getDateOfEvent(), current);
            return unit.getTotalExpense();
        }
    }

    @Override
    public long getCount(EventOfStudent unit) {
        var countableEvent = this.counterMap.get(unit.getDateOfEvent());
        if (countableEvent != null) return countableEvent.getCount();
        else return 0;
    }

    @Override
    public Set<Map.Entry<EventOfStudent, Long>> entrySet() {
        return this.counterMap.values().stream().map((countableEvent -> new Map.Entry<EventOfStudent, Long>() {
            @Override
            public EventOfStudent getKey() {
                return countableEvent.get();
            }

            @Override
            public Long getValue() {
                return countableEvent.getCount();
            }

            @Override
            public Long setValue(Long value) {
                var old = countableEvent.getCount();
                countableEvent.setCount(value.intValue());
                return old;
            }

            @Override
            public boolean equals(Object o) {
                return super.equals(o);
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }
        })).collect(Collectors.toSet());
    }
}
