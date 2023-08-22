package bhtu.work.tths.share.utils.counter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ComplexCounter<K, C extends Countable<K>> implements Counter<C>{
    private final Map<K, C> counter = new HashMap<>();

    @Override
    public long put(C countable) {
        Countable<K> currentCount = counter.get(countable.getKey());
        if (currentCount == null) {
            counter.put(countable.getKey(), countable);
            return countable.getCount();
        } else {
            var nextCount = currentCount.getCount() + countable.getCount();
            currentCount.setCount(nextCount);
            return nextCount;
        }
    }

    @Override
    public long getCount(C key) {
        return this.counter.get(key.getKey()).getCount();
    }

    @Override
    public void forEach(BiConsumer<? super C, ? super Number> action) {
        this.counter.forEach((_k, c) -> {
            action.accept(c,c.getCount());
        });
    }


}
