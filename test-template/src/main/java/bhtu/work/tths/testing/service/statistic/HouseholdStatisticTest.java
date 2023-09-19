package bhtu.work.tths.testing.service.statistic;

import bhtu.work.tths.testing.Client;
import bhtu.work.tths.testing.template.HttpUtils;
import bhtu.work.tths.testing.template.SingleTestCase;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class HouseholdStatisticTest extends SingleTestCase {
    private static final Logger LOGGER_HOUSEHOLD = LoggerFactory.getLogger(HouseholdStatisticTest.class);
    private final Client client;
    private final String houseNumber;
    private String token;

    public HouseholdStatisticTest(Client client, String houseNumber) {
        this.client = client;
        this.houseNumber = houseNumber;
    }

    @Override
    public void prepare() throws Exception {
        // prepare token
        this.token = client.jwt;
    }

    @Override
    public JSONObject doWork() throws Exception {
        var response = HttpUtils.get2(
                client.baseUrl + "/api/statistic/household-number",
                Map.of("Authorization", token),
                Map.of("filter", houseNumber)
        );

        return new JSONObject(response);
    }

    @Override
    public void valid() throws Exception {
        // todo: unk
    }
}
