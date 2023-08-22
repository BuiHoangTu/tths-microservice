package bhtu.work.tths.share.utils.counter;

import bhtu.work.tths.share.utils.MutableNumber;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class SkipCounter<T> implements AddingCounter<T>{
    private final Map<T, MutableNumber<Long>> counter = new HashMap<>();

    @Override
    public long put(T unit, long count) {
        var currentCount = counter.get(unit);
        if (currentCount == null) {
            counter.put(unit, MutableNumber.of(count));
            return count;
        } else {
            var nextCount = currentCount.get() + count;
            currentCount.set(nextCount);
            return nextCount;
        }
    }

    @Override
    public long getCount(T unit) {
        var count = counter.get(unit);
        if (count != null) return count.get();
        else return 0;
    }

    @Override
    public void forEach(BiConsumer<? super T, ? super Number> action) {
        this.counter.forEach(action);
    }

    @Override
    public long put(T unit) {
        return this.put(unit, 1);
    }
}
