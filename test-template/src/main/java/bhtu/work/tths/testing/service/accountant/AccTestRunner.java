package bhtu.work.tths.testing.service.accountant;

import bhtu.work.tths.testing.Client;
import bhtu.work.tths.testing.service.accountant.awardperiod.GetAwardPeriodTest;
import bhtu.work.tths.testing.service.accountant.awardperiod.UpdateAwardPeriodTest;
import bhtu.work.tths.testing.service.accountant.prizeperiod.GetPrizePeriodTest;
import bhtu.work.tths.testing.service.accountant.prizeperiod.UpdatePrizePeriodTest;
import bhtu.work.tths.testing.template.TestCaseRunner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AccTestRunner extends TestCaseRunner {
    private final Client client;
    private final boolean runUpdateAP;
    private final boolean runGetAP;
    private final boolean runUpdatePP;
    private final boolean runGetPP;

    public AccTestRunner(Client client, boolean runUpdateAP, boolean runGetAP, boolean runUpdatePP, boolean runGetPP) {
        this.client = client;
        this.runUpdateAP = runUpdateAP;
        this.runGetAP = runGetAP;
        this.runUpdatePP = runUpdatePP;
        this.runGetPP = runGetPP;
    }

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

        UpdateAwardPeriodTest updateAP = null;
        if(runUpdateAP) updateAP = new UpdateAwardPeriodTest(awardLevels, client);
        if (runUpdateAP) this.addTestCase(updateAP);

        var getAP = new GetAwardPeriodTest(awardLevels, client, updateAP);
        if (runGetAP) this.addTestCase(getAP);


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

        UpdatePrizePeriodTest updateTest = null;
        if (runUpdatePP) {
            updateTest = new UpdatePrizePeriodTest(rewardTypes, client);
            this.addTestCase(updateTest);
        }
        if (runGetPP) this.addTestCase(new GetPrizePeriodTest(rewardTypes, client, updateTest));
        // update is contained
    }
}
