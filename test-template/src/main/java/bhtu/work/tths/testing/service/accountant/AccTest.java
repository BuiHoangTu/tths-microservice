package bhtu.work.tths.testing.service.accountant;

import bhtu.work.tths.testing.service.accountant.awardperiod.Get;
import vht.testing.TestCaseRunner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AccTest extends TestCaseRunner {

    @Override
    public void makeTestCases() {
        List<Map<String, ?>> awardLevels = new LinkedList<>();
        var awardLevel = new HashMap<String, Object>(2);
        awardLevel.put("achievement", "gioi");
        awardLevel.put("prizeValue", 1000);
        awardLevels.add(awardLevel);
        awardLevel = new HashMap<>(2);
        awardLevel.put("achievement", "tot");
        awardLevel.put("prizeValue", 10);
        awardLevels.add(awardLevel);
        this.addTestCase(new Get(awardLevels));

        // other type of level
        var rewardTypes = new LinkedList<Map<String, ?>>();
        awardLevel = new HashMap<>(2);
        awardLevel.put("unitPrice", 3300);
        awardLevel.put("nameOfPrize", "keo (cai)");
        rewardTypes.add(awardLevel);

        awardLevel = new HashMap<>(2);
        awardLevel.put("unitPrice", 5400);
        awardLevel.put("nameOfPrize", "gao (kg)");
        rewardTypes.add(awardLevel);
        this.addTestCase(new bhtu.work.tths.testing.service.accountant.prizeperiod.Get(rewardTypes));
    }
}
