package bhtu.work.tths.testing.service.accountant.prizeperiod;

import bhtu.work.tths.testing.Client;
import bhtu.work.tths.testing.template.DependentTestCase;
import bhtu.work.tths.testing.template.HttpUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GetPrizePeriodTest extends DependentTestCase {
    private final List<Map<String, ?>> expectedAwardLevels;
    private final Client client;

    public GetPrizePeriodTest(List<Map<String, ?>> expectedAwardLevels, Client client, UpdatePrizePeriodTest updateTest) {
        super(new ArrayList<>());
        if (updateTest != null) preCombinedSingleTestCases.add(updateTest);
        this.expectedAwardLevels = expectedAwardLevels;
        this.client = client;
    }

    @Override
    public void prepare() throws Exception {
    }

    @Override
    public JSONObject doWork() throws Exception {
        Thread.sleep(2000); // wait for update
        return new JSONObject(HttpUtils.get2(
                client .baseUrl + "/api/prize-period/get",
                Collections.emptyMap(),
                Collections.emptyMap()
        ));
    }

    @Override
    public void valid() throws Exception {
        var expectedJson = new JSONObject();
        expectedJson.put("rewardTypes", new JSONArray(expectedAwardLevels));
        if (
                responseObject.get("rewardTypes").toString()
                        .equals(
                expectedJson.get("rewardTypes").toString()
                        )) return;

        System.out.printf("expect_: <<%s>> \nbut got: <<%s>>", expectedJson.get("rewardTypes").toString(), responseObject.get("rewardTypes").toString());

        throw new Exception("not the same");
    }
}
