package bhtu.work.tths.share.utils.counter;

/**
 * Represent something that can be counted. Can be used as a wrapper class
 * @param <K> type of key of this countable
 */
public interface Countable<K> {
    static <K> Countable<K> of(K key, long counting) {
        return Countable.of(key, null, counting);
    }
    static <K, C> Countable<K> of(K key, C content, long counting) {
        return new Countable<K>() {
            private long track = counting;

            @Override
            public K getKey() {
                return key;
            }

            @Override
            public long getCount() {
                return track;
            }

            @Override
            public void setCount(long newCount) {
                track = newCount;
            }

            @Override
            public C get() {
                return content;
            }

            @Override
            public int hashCode() {
                return this.getKey().hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof Countable<?> countable) {
                    return countable.getKey().equals(this.getKey()) && countable.get().equals(this.get());
                }
                return false;
            }
        };
    }
    static <K> Countable<K> asKey(K key) {
        final String MESSAGE = "This object is only use as key, try using [of] function";
        return new Countable<K>() {
            @Override
            public K getKey() {
                return key;
            }

            @Override
            public long getCount() {
                throw new UnsupportedOperationException(MESSAGE);
            }

            @Override
            public void setCount(long newCount) {
                throw new UnsupportedOperationException(MESSAGE);
            }
        };
    }
    K getKey();
    long getCount();
    void setCount(long newCount);
    default Object get(){
        return this;
    }
}
