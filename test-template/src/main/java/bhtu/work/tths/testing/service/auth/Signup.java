package bhtu.work.tths.testing.service.auth;

import org.json.JSONObject;
import vht.testing.SingleTestCase;
import vht.testing.example1.testcase.HttpUtil;

public class Signup extends SingleTestCase {
    private final JSONObject request = new JSONObject();

    private final String username;
    private final String password;
    private final String houseNumber;


    public Signup(String username1, String password1, String houseNumber1) {
        this.username = username1;
        this.password = password1;
        this.houseNumber = houseNumber1;
    }

    @Override
    public void prepare() throws Exception {
        request
                .put("username", username)
                .put("password", password)
                .put("householdNumber", houseNumber);
    }

    @Override
    public JSONObject doWork() throws Exception {
        var response = HttpUtil.post2("http://127.0.0.1:8080/api/auth/signup", request.toString());

        return new JSONObject(response);
    }

    @Override
    public void valid() throws Exception {
        if (responseObject.get("Created").equals("true")) return;

        throw new Exception("Can't create");
    }
}
