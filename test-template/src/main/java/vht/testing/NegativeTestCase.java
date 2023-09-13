package vht.testing;

import org.json.JSONObject;

public class NegativeTestCase<T extends SingleTestCase> extends SingleTestCase {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(NegativeTestCase.class);
    protected final T preCombinedSingleTestCase;

    public NegativeTestCase(T preCombinedSingleTestCase2) {
        preCombinedSingleTestCase = preCombinedSingleTestCase2;
    }

    public T getSuperTestCase() {
        return preCombinedSingleTestCase;
    }

    @Override
    public void test() throws Exception {
        try {
            preCombinedSingleTestCase.test();
        } catch (Exception e) { // catch exception is fine
            log.info("Catch Exception, pass " + preCombinedSingleTestCase.getClass().getName());
            return;
        }
        throwException("Not detect exception from " + preCombinedSingleTestCase.getClass().getName());
    }

    @Override
    public void prepare() throws Exception {
        //preCombinedSingleTestCase.prepair();
    }

    @Override
    public JSONObject doWork() throws Exception {
        //return preCombinedSingleTestCase.doWork();
        return null;
    }

    @Override
    public void valid() throws Exception {
        //preCombinedSingleTestCase.valid();
    }

}
