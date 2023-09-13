package bhtu.work.tths.testing.service.accountant.awardperiod;

import bhtu.work.tths.testing.service.auth.Login;
import bhtu.work.tths.testing.service.auth.Signup;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vht.testing.SingleTestCase;
import vht.testing.example1.testcase.HttpUtil;

import java.util.*;

public class Update extends SingleTestCase {
    private static final Logger LOGGER_UPDATE = LoggerFactory.getLogger(Update.class);
    private final JSONObject request = new JSONObject();

    private final List<Map<String, ?>> awardLevels;

    public Update(List<Map<String, ?>> awardLevels) {
        this.awardLevels = awardLevels;
    }

    @SuppressWarnings({"unused"})
    private Update() {
        this.awardLevels = new LinkedList<>();

        var awardLevel = new HashMap<String, String>(2);
        awardLevel.put("achievement", "gioi");
        awardLevel.put("prizeValue", "1000");
        awardLevels.add(awardLevel);

        awardLevel = new HashMap<>(2);
        awardLevel.put("achievement", "gioi");
        awardLevel.put("prizeValue", "1000");
        awardLevels.add(awardLevel);
    }


    @Override
    public void prepare() throws Exception {
        var awardLevels = new JSONArray(this.awardLevels);

        request
                .put("awardLevels", awardLevels);
    }

    @Override
    public JSONObject doWork() throws Exception {
        var login = new Login((Signup) null, "admin", "admin2"); // must be this role to update
        login.test();
        try {
            var token = login.responseObject.get("authorization");
            var response = HttpUtil.put2(
                    "http://127.0.0.1:8080/api/award-period/update",
                    request.toString(),
                    Map.of("Authorization", token.toString()),
                    Collections.emptyMap()
            );
            return new JSONObject(response);
        } catch (JSONException e) {
            LOGGER_UPDATE.info("{}: Can't find symbol in this {}", e, login.responseObject);
            Thread.sleep(1000);
            throw e;
        }


    }

    @Override
    public void valid() throws Exception {
        if (responseObject.get("isUpdated").equals(Boolean.TRUE) ) return;
        throw new Exception("Not updated");
    }
}
