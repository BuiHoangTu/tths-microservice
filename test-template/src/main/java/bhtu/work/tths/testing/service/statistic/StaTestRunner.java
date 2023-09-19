package bhtu.work.tths.testing.service.statistic;

import bhtu.work.tths.testing.Client;
import bhtu.work.tths.testing.template.TestCaseRunner;

public class StaTestRunner extends TestCaseRunner {
    private final Client client;

    public StaTestRunner(Client client) {

        this.client = client;
    }

    @Override
    public void makeTestCases() {
        addTestCase(new EventStatisticTest(client, "name", "Tet"));
        addTestCase(new HouseholdStatisticTest(client, "1"));
    }
}
