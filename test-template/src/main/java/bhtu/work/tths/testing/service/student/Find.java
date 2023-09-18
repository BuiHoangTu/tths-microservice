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

public class Find extends SingleTestCase {
    private static final Logger LOGGER_FIND = LoggerFactory.getLogger(Find.class);
    private String token;
    private final String category;
    private final String filter;

    public Find(String category, String filter) {
        this.category = category;
        this.filter = filter;
    }

    @Override
    public void prepare() throws Exception {
        // prepare token
        var login = new Login((Signup) null, "admin", "admin2"); // must be this role to update
        login.test();
        try {
            this.token = login.responseObject.get("authorization").toString();

        } catch (JSONException e) {
            LOGGER_FIND.info("{}: Can't find symbol in this {}", e, login.responseObject);
            throw e;
        }
    }

    @Override
    public JSONObject doWork() throws Exception {
        var res = HttpUtils.get2(
                "http://127.0.0.1:8080/api/student/find",
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
