package bhtu.work.tths.testing.service.statistic;

import bhtu.work.tths.testing.Client;
import bhtu.work.tths.testing.template.TestCaseRunner;

public class StaTestRunner extends TestCaseRunner {
    private final Client client;
    private final boolean runEvent;
    private final boolean runHousehold;

    public StaTestRunner(Client client, boolean runEvent, boolean runHousehold) {

        this.client = client;
        this.runEvent = runEvent;
        this.runHousehold = runHousehold;
    }

    @Override
    public void makeTestCases() {
        if (runEvent) addTestCase(new EventStatisticTest(client, "name", "Tet"));
        if(runHousehold) addTestCase(new HouseholdStatisticTest(client, "1"));
    }
}
