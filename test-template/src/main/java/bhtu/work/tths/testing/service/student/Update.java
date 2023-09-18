package bhtu.work.tths.testing.service.student;

import bhtu.work.tths.testing.service.auth.Login;
import bhtu.work.tths.testing.service.auth.Signup;
import bhtu.work.tths.testing.template.HttpUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vht.testing.SingleTestCase;

import java.util.Collections;
import java.util.Map;

public class Update extends SingleTestCase {
    private static final Logger LOGGER_UPDATE = LoggerFactory.getLogger(Update.class);
    private JSONObject request = new JSONObject();
    private final String studentName;
    private final String jsonSource;
    private String token;

    public Update(String firstStudentWithName, String studentOneReward) {
        this.studentName = firstStudentWithName;
        this.jsonSource = studentOneReward;
    }

    @Override
    public void prepare() throws Exception {
        // prepare data
        request = new JSONObject(jsonSource);

        // prepare token
        var login = new Login((Signup) null, "admin", "admin2"); // must be this role to update
        login.test();
        try {
            this.token = login.responseObject.get("authorization").toString();

        } catch (JSONException e) {
            LOGGER_UPDATE.info("{}: Can't find symbol in this {}", e, login.responseObject);
            throw e;
        }

        // insert id
        var find = new Find("name", studentName);
        find.test();
        try {
            // students -> first -> id
            String studentId = find.responseObject.getJSONArray("students").getJSONObject(0).get("id").toString();
            request.put("id", studentId);
        } catch (Exception e) {
            LOGGER_UPDATE.info("Can't find this student: {}", ((Object) e));
            throw e;
        }
    }

    @Override
    public JSONObject doWork() throws Exception {
        var res = HttpUtils.put2(
                "http://127.0.0.1:8080/api/student/update",
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
