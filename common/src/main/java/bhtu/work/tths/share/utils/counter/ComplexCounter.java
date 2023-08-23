package bhtu.work.tths.share.utils.counter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Implementation using to counting the Countable
 * @param <K> Key of Countable
 * @param <T> Content of Countable
 */
public class ComplexCounter<K, T> implements Counter<Countable<K, T>>{
    private final Map<K, Countable<K, T>> counter = new HashMap<>();

    @Override
    public long put(Countable<K, T> countable) {
        Countable<K, T> currentCount = counter.get(countable.getKey());
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
    public long getCount(Countable<K,T> key) {
        return this.counter.get(key.getKey()).getCount();
    }

    @Override
    public void forEach(BiConsumer<? super Countable<K,T>, ? super Number> action) {
        this.counter.forEach((_k, c) -> action.accept(c,c.getCount()));
    }


}
