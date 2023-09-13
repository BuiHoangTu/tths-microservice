package bhtu.work.tths.testing.service.student;

import bhtu.work.tths.testing.service.auth.Login;
import bhtu.work.tths.testing.service.auth.Signup;
import bhtu.work.tths.testing.template.HttpUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vht.testing.SingleTestCase;

import java.util.Collections;
import java.util.Map;

public class Add extends SingleTestCase {
    private static final Logger LOGGER_ADD = LoggerFactory.getLogger(Add.class);
    private JSONObject request = new JSONObject();
    private final String jsonSource;
    private String token;

    public Add(String jsonSource) {
        this.jsonSource = jsonSource;
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
            LOGGER_ADD.info("{}: Can't find symbol in this {}", e, login.responseObject);
            throw e;
        }
    }

    @Override
    public JSONObject doWork() throws Exception {
        var res = HttpUtils.post2(
                "http://127.0.0.1:8080/api/student/add",
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
