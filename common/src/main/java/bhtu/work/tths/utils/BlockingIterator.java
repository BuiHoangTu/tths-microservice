package bhtu.work.tths.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingIterator<T> implements Iterator<T>, AutoCloseable{
    private final BlockingQueue<T> content;
    private boolean isSendingFinished = false;
    private T currentElement = null;


    public BlockingIterator() {
        this. content = new LinkedBlockingQueue<>();
    }
    public BlockingIterator(BlockingQueue<T> content) {
        this.content = content;
    }


    private T getCurrent() throws InterruptedException {
        if (currentElement == null) {
            // source is still sending or content is still holding elements
            if (!isSendingFinished || !content.isEmpty()) currentElement = content.take();
            else throw new InterruptedException("No more element");
        }

        return currentElement;
    }


    @Override
    public boolean hasNext() {
        try {
            if (this.getCurrent() != null) {
                return true;
            }
        } catch (InterruptedException _e) {
            return false;
        }

        return false;
    }

    @Override
    public T next() {
        try {
            // get currentE out so currentElement is now null
            var current = getCurrent();
            this.currentElement = null;
            return current;
        } catch (InterruptedException e) {
            throw new NoSuchElementException(e);
        }
    }

    @Override
    public void close() {
        this.isSendingFinished = true;
    }

    public boolean offer(T t) {
        if (isSendingFinished) throw new IllegalStateException("Sending is closed, can't offer more data.");
        return content.offer(t);
    }
}
