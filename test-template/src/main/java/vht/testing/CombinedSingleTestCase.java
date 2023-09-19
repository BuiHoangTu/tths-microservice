/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vht.testing;

public abstract class CombinedSingleTestCase<T extends SingleTestCase> extends SingleTestCase {

    protected final T preCombinedSingleTestCase;

    public CombinedSingleTestCase(T preCombinedSingleTestCase2) {
        this(preCombinedSingleTestCase2, SingleTestCase.defaultExpectedResultCode);
    }

    public CombinedSingleTestCase(T preCombinedSingleTestCase2, int expectedResultCode2) {
        this(preCombinedSingleTestCase2, "" + expectedResultCode2);
    }

    public CombinedSingleTestCase(T preCombinedSingleTestCase2, String expectedResultCode2) {
        super(expectedResultCode2);
        preCombinedSingleTestCase = preCombinedSingleTestCase2;
    }

    public CombinedSingleTestCase(T preCombinedSingleTestCase2, String expectedResultField2, String expectedResultCode2) {
        super(expectedResultField2, expectedResultCode2);
        preCombinedSingleTestCase = preCombinedSingleTestCase2;
    }

    public T getSuperTestCase() {
        return preCombinedSingleTestCase;
    }

    @Override
    public void test() throws Exception {
        if (preCombinedSingleTestCase != null) {
            preCombinedSingleTestCase.test();
        }
        super.test();
    }

}
