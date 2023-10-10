/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bhtu.work.tths.testing.template;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Ensure that every test cases that this depends on are all run before
 */
public abstract class DependentTestCase extends SingleTestCase {

    protected final List<SingleTestCase> preCombinedSingleTestCases = new LinkedList<>();

    public DependentTestCase(List<? extends SingleTestCase> preCombinedSingleTestCases) {
        this(preCombinedSingleTestCases, SingleTestCase.defaultExpectedResultCode);
    }

    public DependentTestCase(List<? extends SingleTestCase> preCombinedSingleTestCases, int expectedResultCode2) {
        this(preCombinedSingleTestCases, String.valueOf(expectedResultCode2));
    }

    public DependentTestCase(List<? extends SingleTestCase> preCombinedSingleTestCases, String expectedResultCode2) {
        super(expectedResultCode2);
        this.preCombinedSingleTestCases.addAll(preCombinedSingleTestCases);
    }

    public DependentTestCase(List<? extends SingleTestCase> preCombinedSingleTestCases, String expectedResultField2, String expectedResultCode2) {
        super(expectedResultField2, expectedResultCode2);
        this.preCombinedSingleTestCases.addAll(preCombinedSingleTestCases);
    }

    public List<SingleTestCase> getDependingTestCases() {
        return Collections.unmodifiableList(preCombinedSingleTestCases);
    }

    @Override
    public void test() throws Exception {
        if (!preCombinedSingleTestCases.isEmpty()) {
            for (SingleTestCase testCase : preCombinedSingleTestCases) {
                testCase.test();
            }
        }
        super.test();
    }

}
