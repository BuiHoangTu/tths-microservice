package bhtu.work.tths.testing.service.accountant.prizeperiod;

import org.json.JSONArray;
import org.json.JSONObject;
import vht.testing.CombinedSingleTestCase;
import vht.testing.example1.testcase.HttpUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Get extends CombinedSingleTestCase<Update> {
    private final List<Map<String, ?>> expectedAwardLevels;

    public Get(List<Map<String, ?>> expectedAwardLevels) {
        super(new Update(expectedAwardLevels));
        this.expectedAwardLevels = expectedAwardLevels;

    }

    @Override
    public void prepare() throws Exception {
    }

    @Override
    public JSONObject doWork() throws Exception {
        Thread.sleep(2000); // wait for update
        return new JSONObject(HttpUtil.get2(
                "http://localhost:8080/api/prize-period/get",
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
