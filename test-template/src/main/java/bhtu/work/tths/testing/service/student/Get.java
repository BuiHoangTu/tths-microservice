package bhtu.work.tths.testing.service.student;

import bhtu.work.tths.testing.service.auth.Login;
import bhtu.work.tths.testing.service.auth.Signup;
import bhtu.work.tths.testing.template.HttpUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vht.testing.SingleTestCase;

import java.util.Map;

public class Get extends SingleTestCase {
    private static final Logger LOGGER_GET = LoggerFactory.getLogger(Get.class);
    private String token;
    private final String id;

    public Get(String id) {
        this.id = id;
    }

    @Override
    public void prepare() throws Exception {
        // prepare token, only parent-access is required
        var login = new Login((Signup) null, "test_user5", "test_pwd"); // must be this role to update
        login.test();
        try {
            this.token = login.responseObject.get("authorization").toString();

        } catch (JSONException e) {
            LOGGER_GET.info("{}: Can't find symbol in this {}", e, login.responseObject);
            throw e;
        }
    }

    @Override
    public JSONObject doWork() throws Exception {
        var res = HttpUtils.get2(
                "http://127.0.0.1:8080/api/student/get",
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
