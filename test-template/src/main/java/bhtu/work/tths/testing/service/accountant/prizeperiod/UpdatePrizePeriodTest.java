package bhtu.work.tths.testing.service.accountant.prizeperiod;

import bhtu.work.tths.testing.Client;
import bhtu.work.tths.testing.template.HttpUtils;
import bhtu.work.tths.testing.template.SingleTestCase;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class UpdatePrizePeriodTest extends SingleTestCase {
    private static final Logger LOGGER_UPDATE = LoggerFactory.getLogger(UpdatePrizePeriodTest.class);
    private final JSONObject request = new JSONObject();
    private String token;
    private final List<Map<String, ?>> awardLevels;
    private final Client client;

    public UpdatePrizePeriodTest(List<Map<String, ?>> awardLevels, Client client) {
        this.awardLevels = awardLevels;
        this.client = client;
    }


    @Override
    public void prepare() throws Exception {
        // prepare request
        var awardLevels = new JSONArray(this.awardLevels);
        request.put("rewardTypes", awardLevels);
        this.token = this.client.jwt;
    }

    @Override
    public JSONObject doWork() throws Exception {
        var response = HttpUtils.put2(
                client .baseUrl  + "/api/prize-period/update",
                request.toString(),
                Map.of("Authorization", token),
                Collections.emptyMap()
        );
        return new JSONObject(response);
    }

    @Override
    public void valid() throws Exception {
        if (responseObject.get("isUpdated").equals(Boolean.TRUE)) return;
        throw new Exception("Not updated");
    }
}
