package bhtu.work.tths.testing.service.student;

import bhtu.work.tths.testing.Client;
import bhtu.work.tths.testing.template.HttpUtils;
import bhtu.work.tths.testing.template.SingleTestCase;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class GetStudentTest extends SingleTestCase {
    private static final Logger LOGGER_GET = LoggerFactory.getLogger(GetStudentTest.class);
    private String token;
    private final Client client;
    private final String id;

    public GetStudentTest(Client client, String id) {
        this.client = client;
        this.id = id;
    }

    @Override
    public void prepare() throws Exception {
        // prepare token, only parent-access is required
token = client.jwt;    }

    @Override
    public JSONObject doWork() throws Exception {
        var res = HttpUtils.get2(
                client.baseUrl + "/api/student/get",
                Map.of("Authorization", token),
                Map.of("id", id)
        );
        return new JSONObject(res);
    }

    @Override
    public void valid() throws Exception {
        // todo: unk
    }

}
