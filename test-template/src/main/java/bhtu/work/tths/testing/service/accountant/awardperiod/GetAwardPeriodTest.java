package bhtu.work.tths.testing.service.accountant.awardperiod;

import bhtu.work.tths.testing.Client;
import bhtu.work.tths.testing.service.auth.SignupTest;
import bhtu.work.tths.testing.template.DependentTestCase;
import bhtu.work.tths.testing.template.HttpUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class GetAwardPeriodTest extends DependentTestCase {
    private final List<Map<String, ?>> expectedAwardLevels;
    private final Client client;

    public GetAwardPeriodTest(List<Map<String, ?>> expectedAwardLevels, Client client, UpdateAwardPeriodTest updateTest) {
        super(List.of(updateTest));
        this.expectedAwardLevels = expectedAwardLevels;

        this.client = client;
    }

    @Override
    public void prepare() throws Exception {
    }

    @Override
    public JSONObject doWork() throws Exception {
        return new JSONObject(HttpUtils.get2(
                client .baseUrl + "/api/award-period/get",
                Collections.emptyMap(),
                Collections.emptyMap()
//  get from specific date              Map.of("date", "1992-01-01T00:00:00.000Z")
        ));
    }

    @Override
    public void valid() throws Exception {
        var expectedJson = new JSONObject();
        expectedJson.put("awardLevels", new JSONArray(expectedAwardLevels));

        if (Objects.equals(
                        responseObject.get("awardLevels").toString(),
                        expectedJson.get("awardLevels").toString()
        )) return;

        System.out.printf("expect: <<%s>> /n but got <<%s>>", expectedJson.get("awardLevels").toString(), responseObject.get("awardLevels").toString());

        throw new Exception("not the same");
    }
}
