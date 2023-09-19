package bhtu.work.tths.testing.service.statistic;

import bhtu.work.tths.testing.Client;
import bhtu.work.tths.testing.template.HttpUtils;
import bhtu.work.tths.testing.template.SingleTestCase;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class EventStatisticTest extends SingleTestCase {
    private static final Logger LOGGER_EVENT = LoggerFactory.getLogger(EventStatisticTest.class);
    private final Client client;
    private final String searchType;
    private final String searchValue;
    private String token;

    public EventStatisticTest(Client client, String searchType, String searchValue) {
        this.client = client;
        this.searchType = searchType;
        this.searchValue = searchValue;
    }

    @Override
    public void prepare() throws Exception {
        this.token = client.jwt;
    }

    @Override
    public JSONObject doWork() throws Exception {
        var response = HttpUtils.get2(
                client.baseUrl + "/api/statistic/event",
                Map.of("Authorization", token),
                Map.of("filter", searchValue, "type", searchType)
        );

        return new JSONObject().put("events", new JSONArray(response));
    }

    @Override
    public void valid() throws Exception {
        // todo: unk
    }
}
