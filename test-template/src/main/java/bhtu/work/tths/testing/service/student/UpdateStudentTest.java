package bhtu.work.tths.testing.service.student;

import bhtu.work.tths.testing.Client;
import bhtu.work.tths.testing.template.DependentTestCase;
import bhtu.work.tths.testing.template.HttpUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UpdateStudentTest extends DependentTestCase {
    private static final Logger LOGGER_UPDATE = LoggerFactory.getLogger(UpdateStudentTest.class);
    private JSONObject request = new JSONObject();
    private final Client client;
    private final FindStudentTest findTest;
    private final String studentName;
    private final String jsonSource;
    private String token;

    public UpdateStudentTest(Client client, FindStudentTest findStudentTest, String firstStudentWithName, String studentOneReward) {
        super(new ArrayList<>());
        this.preCombinedSingleTestCases.add(findStudentTest);
        this.client = client;
        this.findTest = findStudentTest;
        this.studentName = firstStudentWithName;
        this.jsonSource = studentOneReward;
    }

    @Override
    public void prepare() throws Exception {
        // prepare data
        request = new JSONObject(jsonSource);

        // prepare token
        this.token = client.jwt;

        // insert id
        var findTest = getDependingTestCases().get(0);
        // students -> first -> id
        String studentId = findTest.responseObject.getJSONArray("students").getJSONObject(0).get("id").toString();
        request.put("id", studentId);
    }

    @Override
    public JSONObject doWork() throws Exception {
        var res = HttpUtils.put2(
                client.baseUrl + "/api/student/update",
                request.toString(),
                Map.of("Authorization", token),
                Collections.emptyMap()
        );

        try {
            return new JSONObject(res);
        } catch (Exception e) {
            LOGGER_UPDATE.info(res);
            throw e;
        }
    }

    @Override
    public void valid() throws Exception {
        if (responseObject.get("isUpdated").equals(Boolean.TRUE)) return;
        throw new Exception("Not updated");
    }

}
