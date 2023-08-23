package bhtu.work.tths.share.utils.counter;

/**
 * Represent something that can be counted. Can be used as a wrapper class
 * @param <K> type of key of this countable
 */
public interface Countable<K, T> {
    static <K> Countable<K, Countable> of(K key, long counting) {
        return new Countable<>() {
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
            public Countable get() {
                return this;
            }

            @Override
            public int hashCode() {
                return this.getKey().hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof Countable<?, ?> countable) {
                    return countable.getKey().equals(this.getKey()) && countable.get().equals(this.get());
                }
                return false;
            }
        };
    }
    static <K, T> Countable<K, T> of(K key, T content, long counting) {
        return new Countable<>() {
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
            public T get() {
                return content;
            }

            @Override
            public int hashCode() {
                return this.getKey().hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof Countable<?,?> countable) {
                    return countable.getKey().equals(this.getKey()) && countable.get().equals(this.get());
                }
                return false;
            }
        };
    }
    static <K> Countable<K, Countable> asKey(K key) {
        final String MESSAGE = "This object is only use as key, try using [of] function";
        return new Countable<>() {
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

            @Override
            public Countable get() {
                return this;
            }
        };
    }
    K getKey();
    long getCount();
    void setCount(long newCount);
    T get();
}
