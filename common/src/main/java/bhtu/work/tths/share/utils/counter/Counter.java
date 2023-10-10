package bhtu.work.tths.share.utils.counter;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public interface Counter<T>{
    /**
     * Put to counter
     * @return counting number after put this value in.
     */
    long put(T unit);
    default void putAll(Collection<T> m) {
        m.forEach(this::put);
    }
    long getCount(T unit);
    Set<Map.Entry<T, Long>> entrySet();
}
