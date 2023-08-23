package bhtu.work.tths.statisticservice.utils;

import bhtu.work.tths.share.utils.counter.ComplexCounter;
import bhtu.work.tths.share.utils.counter.Countable;
import bhtu.work.tths.share.utils.counter.Counter;
import bhtu.work.tths.statisticservice.models.PrizeGroup;
import lombok.NonNull;

import java.util.*;
import java.util.function.BiConsumer;

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
    public void forEach(BiConsumer<? super PrizeGroup, ? super Number> action) {
        this.counter.forEach((pgc, _v) -> action.accept(pgc.get(), pgc.getCount()));
    }

    public java.util.Collection<PrizeGroup> values() {
        Collection<PrizeGroup> out = new ArrayList<>();
        this.forEach(((prizeGroup, number) -> out.add(prizeGroup)));
        return out;
    }
}
