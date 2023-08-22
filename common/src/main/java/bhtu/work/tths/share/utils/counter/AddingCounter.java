package bhtu.work.tths.share.utils.counter;

public interface AddingCounter<T> extends Counter<T>{
    long put(T unit, long count);
}
