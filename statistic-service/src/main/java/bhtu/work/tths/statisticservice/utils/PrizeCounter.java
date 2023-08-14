package bhtu.work.tths.statisticservice.utils;

import bhtu.work.tths.statisticservice.models.PrizeGroup;
import org.bson.assertions.Assertions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PrizeCounter implements Map<String, PrizeGroup> {
    private Map<String, PrizeGroup> counter = new HashMap<>();

    @Override
    public int size() {
        return counter.size();
    }

    @Override
    public boolean isEmpty() {
        return counter.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return counter.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return counter.containsValue(value);
    }

    @Override
    public PrizeGroup get(Object key) {
        return counter.get(key);
    }

    /**
     *
     * @return new PrizeGroup after put in
     */
    @Override
    public PrizeGroup put(String key, PrizeGroup value) {
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
    public int put(PrizeGroup value) {
        return this.put(value.getNameOfPrize(), value).getAmount();
    }

    @Override
    public PrizeGroup remove(Object key) {
        return counter.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends PrizeGroup> m) {
        for(var pg: m.entrySet()) {
            this.put(pg.getKey(), pg.getValue());
        }
    }
    public void putAll(Collection<PrizeGroup> pgs) {
        for(var pg : pgs){
            this.put(pg);
        }
    }

    @Override
    public void clear() {
        this.counter.clear();
    }

    @Override
    public Set<String> keySet() {
        return counter.keySet();
    }

    @Override
    public Collection<PrizeGroup> values() {
        return counter.values();
    }

    @Override
    public Set<Entry<String, PrizeGroup>> entrySet() {
        return counter.entrySet();
    }
}
