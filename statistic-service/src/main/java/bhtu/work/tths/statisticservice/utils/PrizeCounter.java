package bhtu.work.tths.statisticservice.utils;

import bhtu.work.tths.share.utils.Counter;
import bhtu.work.tths.statisticservice.models.PrizeGroup;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PrizeCounter implements Counter<PrizeGroup>, Iterable<PrizeGroup> {
    private final Map<String, PrizeGroup> counter = new HashMap<>();
    /**
     *
     * @return new PrizeGroup after put in
     */
    @NonNull
    private PrizeGroup put(String key, PrizeGroup value) {
        if (value == null) throw new NullPointerException("Can't insert null");

        var old = counter.get(key);
        var newTotal = 0;
        if (old != null) newTotal += old.getAmount();
        newTotal += value.getAmount();

        var nPG = new PrizeGroup(key, newTotal);
        this.counter.put(key, nPG);
        return nPG;
    }

    /**
     *
     * @return new total
     */
    @Override
    public long put(PrizeGroup value) {
        return this.put(value.getNameOfPrize(), value).getAmount();
    }


    @Override
    public long getCount(PrizeGroup key) {
        return counter.get(key.getNameOfPrize()).getAmount();
    }

    public Set<String> nameOfPrizes() {
        return counter.keySet();
    }

    @Override
    public Iterator<PrizeGroup> iterator() {
        return counter.values().iterator();
    }

    public java.util.Collection<PrizeGroup> values() {
        return counter.values();
    }
}
