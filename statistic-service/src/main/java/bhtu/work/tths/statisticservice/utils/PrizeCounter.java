package bhtu.work.tths.statisticservice.utils;

import bhtu.work.tths.share.utils.counter.ComplexCounter;
import bhtu.work.tths.share.utils.counter.Countable;
import bhtu.work.tths.share.utils.counter.Counter;
import bhtu.work.tths.core.models.PrizeGroup;

import java.util.*;
import java.util.stream.Collectors;

public class PrizeCounter implements Counter<PrizeGroup>{
    private static Countable<String, PrizeGroup> warp(PrizeGroup p) {
        return new Countable<>() {
            @Override
            public String getKey() {
                return p.getNameOfPrize();
            }

            @Override
            public long getCount() {
                return p.getAmount();
            }

            @Override
            public void setCount(long newCount) {
                p.setAmount((int) newCount);
            }

            @Override
            public PrizeGroup get() {
                return p;
            }
        };
    }

    private final Counter<Countable<String, PrizeGroup>> counter = new ComplexCounter<>();


    @Override
    public long put(PrizeGroup unit) {
        return this.counter.put(warp(unit));
    }


    @Override
    public long getCount(PrizeGroup unit) {
        return this.counter.getCount(warp(unit));
    }

    @Override
    public Set<Map.Entry<PrizeGroup, Long>> entrySet() {
        return counter.entrySet().stream().map((entry -> new Map.Entry<PrizeGroup, Long>() {
            @Override
            public PrizeGroup getKey() {
                return entry.getKey().get();
            }

            @Override
            public Long getValue() {
                return entry.getValue();
            }

            @Override
            public Long setValue(Long value) {
                return entry.setValue(value);
            }

            @Override
            public boolean equals(Object o) {
                return entry.equals(o);
            }

            @Override
            public int hashCode() {
                return entry.hashCode();
            }
        })).collect(Collectors.toSet());
    }

    public java.util.Set<PrizeGroup> values() {
        Set<PrizeGroup> out = new HashSet<>();
        this.entrySet().forEach((entry) -> out.add(entry.getKey()));
        return out;
    }
}
