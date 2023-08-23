package bhtu.work.tths.share.utils.counter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation using to counting the Countable
 * @param <K> Key of Countable
 * @param <T> Content of Countable
 */
public class ComplexCounter<K, T> implements Counter<Countable<K, T>>{
    private final Map<K, Countable<K, T>> counterMap = new HashMap<>();

    @Override
    public long put(Countable<K, T> countable) {
        Countable<K, T> currentCount = counterMap.get(countable.getKey());
        if (currentCount == null) {
            counterMap.put(countable.getKey(), countable);
            return countable.getCount();
        } else {
            var nextCount = currentCount.getCount() + countable.getCount();
            currentCount.setCount(nextCount);
            return nextCount;
        }
    }

    @Override
    public long getCount(Countable<K,T> key) {
        return this.counterMap.get(key.getKey()).getCount();
    }

    @Override
    public Set<Map.Entry<Countable<K, T>, Long>> entrySet() {
        return counterMap.values().stream().map(countable -> new Map.Entry<Countable<K, T>, Long>() {
            @Override
            public Countable<K, T> getKey() {
                return countable;
            }

            @Override
            public Long getValue() {
                return countable.getCount();
            }

            @Override
            public Long setValue(Long value) {
                var old = countable.getCount();
                countable.setCount(value);
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
        }).collect(Collectors.toSet());
    }


}
