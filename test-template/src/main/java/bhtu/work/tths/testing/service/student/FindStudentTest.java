package bhtu.work.tths.testing.service.student;

import bhtu.work.tths.testing.Client;
import bhtu.work.tths.testing.template.HttpUtils;
import bhtu.work.tths.testing.template.SingleTestCase;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class FindStudentTest extends SingleTestCase {
    private static final Logger LOGGER_FIND = LoggerFactory.getLogger(FindStudentTest.class);
    private String token;
    private final Client client;
    private final String category;
    private final String filter;

    public FindStudentTest(Client client, String category, String filter) {
        this.client = client;
        this.category = category;
        this.filter = filter;
    }

    @Override
    public void prepare() throws Exception {
        // prepare token
        this.token = client.jwt;
    }

    @Override
    public JSONObject doWork() throws Exception {
        var res = HttpUtils.get2(
                client.baseUrl + "/api/student/find",
                Map.of("Authorization", token),
                Map.of("category", category, "filter", filter)
        );
        return new JSONObject().put("students", new JSONArray(res));
    }

    @Override
    public void valid() throws Exception {
        // todo: unk
    }

}
