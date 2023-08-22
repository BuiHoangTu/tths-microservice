package bhtu.work.tths.statisticservice.utils;

import bhtu.work.tths.share.utils.counter.Counter;
import bhtu.work.tths.statisticservice.models.PrizeGroup;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

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
    public long put(PrizeGroup unit) {
        return this.put(unit.getNameOfPrize(), unit).getAmount();
    }


    @Override
    public long getCount(PrizeGroup unit) {
        return counter.get(unit.getNameOfPrize()).getAmount();
    }

    @Override
    public void forEach(BiConsumer<? super PrizeGroup, ? super Number> action) {
        this.counter.forEach((k, v) -> {
            action.accept(v, v.getAmount());
        });
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
