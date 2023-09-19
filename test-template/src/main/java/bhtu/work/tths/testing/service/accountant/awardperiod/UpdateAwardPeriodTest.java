package bhtu.work.tths.testing.service.accountant.awardperiod;

import bhtu.work.tths.testing.Client;
import bhtu.work.tths.testing.template.HttpUtils;
import bhtu.work.tths.testing.template.SingleTestCase;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UpdateAwardPeriodTest extends SingleTestCase {
    private static final Logger LOGGER_UPDATE = LoggerFactory.getLogger(UpdateAwardPeriodTest.class);
    private final JSONObject request = new JSONObject();

    private final List<Map<String, ?>> awardLevels;
    private final Client client;

    public UpdateAwardPeriodTest(List<Map<String, ?>> awardLevels, Client client) {
        this.awardLevels = awardLevels;
        this.client = client;
    }


    @Override
    public void prepare() throws Exception {
        var awardLevels = new JSONArray(this.awardLevels);

        request
                .put("awardLevels", awardLevels);
    }

    @Override
    public JSONObject doWork() throws Exception {
        var token = client.jwt;
        var response = HttpUtils.put2(
                client.baseUrl + "/api/award-period/update",
                request.toString(),
                Map.of("Authorization", token),
                Collections.emptyMap()
            );
        return new JSONObject(response);
    }

    @Override
    public void valid() throws Exception {
        if (responseObject.get("isUpdated").equals(Boolean.TRUE) ) return;
        throw new Exception("Not updated");
    }
}
