package bhtu.work.tths.testing.service.accountant.prizeperiod;

import bhtu.work.tths.testing.service.auth.Login;
import bhtu.work.tths.testing.service.auth.Signup;
import bhtu.work.tths.testing.template.HttpUtils;
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
    private String token;
    private final List<Map<String, ?>> awardLevels;

    public Update(List<Map<String, ?>> awardLevels) {
        this.awardLevels = awardLevels;
    }

    @SuppressWarnings({"unused"})
    private Update() {
        this.awardLevels = new LinkedList<>();

        var awardLevel = new HashMap<String, Object>(2);
        awardLevel.put("unitPrice", 3300);
        awardLevel.put("nameOfPrize", "keo (cai)");
        awardLevels.add(awardLevel);

        awardLevel = new HashMap<>(2);
        awardLevel.put("unitPrice", 5400);
        awardLevel.put("nameOfPrize", "gao (kg)");
        awardLevels.add(awardLevel);
    }


    @Override
    public void prepare() throws Exception {
        // prepare request
        var awardLevels = new JSONArray(this.awardLevels);
        request.put("rewardTypes", awardLevels);

        // prepare token
        var login = new Login((Signup) null, "admin", "admin2"); // must be this role to update
        login.test();
        try {
            this.token = login.responseObject.get("authorization").toString();

        } catch (JSONException e) {
            LOGGER_UPDATE.info("{}: Can't find symbol in this {}", e, login.responseObject);
            throw e;
        }
    }

    @Override
    public JSONObject doWork() throws Exception {
        var response = HttpUtils.put2(
                "http://127.0.0.1:8080/api/prize-period/update",
                request.toString(),
                Map.of("Authorization", token),
                Collections.emptyMap()
        );
        return new JSONObject(response);
    }

    @Override
    public void valid() throws Exception {
        if (responseObject.get("isUpdated").equals(Boolean.TRUE)) return;
        throw new Exception("Not updated");
    }
}