package bhtu.work.tths.share.utils;

/**
 * Represent something that can be counted. Can be used as a wrapper class
 * @param <K> type of key of this countable
 * @param <V> type of thing
 */
public interface Countable<K, V> {
    K getKey();
    long getCount();
    void setCount(long newValue);
    V get();
}
