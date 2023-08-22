package bhtu.work.tths.share.utils;

public interface AddingCounter<T> extends Counter<T>{
    long put(T key, long count);
}
