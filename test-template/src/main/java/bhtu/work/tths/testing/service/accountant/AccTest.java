package bhtu.work.tths.testing.service.accountant;

import bhtu.work.tths.testing.service.accountant.awardperiod.Get;
import vht.testing.TestCaseRunner;

import java.util.List;
import java.util.Map;

public class AccTest extends TestCaseRunner {
    private final List<Map<String, ?>> awardLevels;

    public AccTest(List<Map<String, ?>> awardLevels) {
        this.awardLevels = awardLevels;
    }

    @Override
    public void makeTestCases() {
        this.addTestCase(new Get(awardLevels));
    }
}
