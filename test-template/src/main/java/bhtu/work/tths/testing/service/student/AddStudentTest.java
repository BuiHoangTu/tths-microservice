package bhtu.work.tths.testing.service.student;

import bhtu.work.tths.testing.Client;
import bhtu.work.tths.testing.template.HttpUtils;
import bhtu.work.tths.testing.template.SingleTestCase;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

public class AddStudentTest extends SingleTestCase {
    private static final Logger LOGGER_ADD = LoggerFactory.getLogger(AddStudentTest.class);
    private JSONObject request = new JSONObject();
    private final Client client;
    private final String jsonSource;
    private String token;

    public AddStudentTest(Client client, String jsonSource) {
        this.client = client;
        this.jsonSource = jsonSource;
    }

    @Override
    public void prepare() throws Exception {
        // prepare data
        request = new JSONObject(jsonSource);

        // prepare token
        this.token = client.jwt;

    }

    @Override
    public JSONObject doWork() throws Exception {
        var res = HttpUtils.post2(
                client.baseUrl + "/api/student/add",
                request.toString(),
                Map.of("Authorization", token),
                Collections.emptyMap()
        );
        return new JSONObject(res);
    }

    @Override
    public void valid() throws Exception {
        if (responseObject.get("isAdded").equals(Boolean.TRUE)) return;
        throw new Exception("Not added");
    }
}
