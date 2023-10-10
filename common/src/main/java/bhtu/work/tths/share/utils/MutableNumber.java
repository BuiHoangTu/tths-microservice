package bhtu.work.tths.share.utils;

public class MutableNumber<N extends Number> extends Number{
    public static <N extends Number> MutableNumber<N> of(N number) {
        if (number == null) throw new NullPointerException();
        return new MutableNumber<>(number);
    }

    private MutableNumber(N number){
        this.number = number;
    }

    private N number;

    @Override
    public int intValue() {
        return number.intValue();
    }

    @Override
    public long longValue() {
        return number.longValue();
    }

    @Override
    public float floatValue() {
        return number.floatValue();
    }

    @Override
    public double doubleValue() {
        return number.doubleValue();
    }

    @Override
    public byte byteValue() {
        return number.byteValue();
    }

    @Override
    public short shortValue() {
        return number.shortValue();
    }

    public void set(N number) {
        this.number = number;
    }

    public N get() {
        return number;
    }
}
