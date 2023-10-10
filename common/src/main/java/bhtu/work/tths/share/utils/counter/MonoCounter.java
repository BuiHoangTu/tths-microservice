package bhtu.work.tths.share.utils.counter;

import bhtu.work.tths.share.utils.MutableNumber;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Simple implementation of Counter.
 * Each put increase counter by 1
 * @param <T> Type of thing to count preferably immutable.
 *           Mutate the object while counting may result in unexpected behavior.
 */
public class MonoCounter<T> implements Counter<T>{
    private final Map<T, MutableNumber<Long>> counterMap = new HashMap<>();

    @Override
    public long put(T unit) {
        var currentCount = counterMap.get(unit);
        if (currentCount == null) {
            counterMap.put(unit, MutableNumber.of(1L));
            return 1;
        } else {
            var nextCount = currentCount.get() + 1;
            currentCount.set(nextCount);
            return nextCount;
        }
    }

    @Override
    public long getCount(T unit) {
        var count = counterMap.get(unit);
        if (count != null) return count.get();
        else return 0;
    }

    @Override
    public Set<Map.Entry<T, Long>> entrySet() {
        return this.counterMap.entrySet().stream().map((entry) -> new Map.Entry<T, Long>() {
            @Override
            public T getKey() {
                return entry.getKey();
            }

            @Override
            public Long getValue() {
                return entry.getValue().get();
            }

            @Override
            public Long setValue(Long value) {
                return entry.setValue(MutableNumber.of(value)).get();
            }
        }).collect(Collectors.toSet());
    }

}
