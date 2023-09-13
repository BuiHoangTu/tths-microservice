package bhtu.work.tths.testing.service.statistic;

import bhtu.work.tths.testing.service.auth.Login;
import bhtu.work.tths.testing.service.auth.Signup;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vht.testing.SingleTestCase;
import vht.testing.example1.testcase.HttpUtil;

import java.util.Map;

public class Household extends SingleTestCase {
    private static final Logger LOGGER_HOUSEHOLD = LoggerFactory.getLogger(Household.class);
    private String token;

    @Override
    public void prepare() throws Exception {
        // prepare token
        var login = new Login((Signup) null, "admin", "admin2"); // must be this role to update
        login.test();
        try {
            this.token = login.responseObject.get("authorization").toString();

        } catch (JSONException e) {
            LOGGER_HOUSEHOLD.info("{}: Can't find symbol in this {}", e, login.responseObject);
            throw e;
        }
    }

    @Override
    public JSONObject doWork() throws Exception {
        var response = HttpUtil.get2(
                "http://127.0.0.1:8080/api/statistic/household-number",
                Map.of("Authorization", token),
                Map.of("filter", "16894577")
        );

        return new JSONObject(response);
    }

    @Override
    public void valid() throws Exception {
        // todo: unk
    }
}
