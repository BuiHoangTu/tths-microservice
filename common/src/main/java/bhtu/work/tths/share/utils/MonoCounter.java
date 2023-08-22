package bhtu.work.tths.share.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Simple implementation of Counter.
 * Each put increase counter by 1
 * @param <T> Type of thing to count preferably immutable.
 *           Mutate the object while counting may result in unexpected behavior.
 */
public class MonoCounter<T> implements Counter<T>{
    private final Map<T, MutableNumber<Long>> counter = new HashMap<>();

    @Override
    public long put(T key) {
        var currentCount = counter.get(key);
        if (currentCount == null) {
            counter.put(key, MutableNumber.of(1L));
            return 1;
        } else {
            var nextCount = currentCount.get() + 1;
            currentCount.set(nextCount);
            return nextCount;
        }
    }

    @Override
    public long getCount(T key) {
        var count = counter.get(key);
        if (count != null) return count.get();
        else return 0;
    }
}
