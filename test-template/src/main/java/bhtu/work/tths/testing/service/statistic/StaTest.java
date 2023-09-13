package bhtu.work.tths.testing.service.statistic;

import vht.testing.TestCaseRunner;

public class StaTest extends TestCaseRunner {
    @Override
    public void makeTestCases() {
        addTestCase(new Event());
        addTestCase(new Household());
    }
}
