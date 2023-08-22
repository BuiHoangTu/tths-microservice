package bhtu.work.tths.share.utils;

import java.util.Collection;
import java.util.function.BiConsumer;

public interface Counter<T>{
    /**
     * Put to counter
     * @return counting number after put this value in.
     */
    long put(T key);
    default void putAll(Collection<T> m) {
        m.forEach(this::put);
    }
    long getCount(T key);
}
