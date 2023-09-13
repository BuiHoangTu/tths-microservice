package bhtu.work.tths.testing.service.auth;

import org.json.JSONObject;
import vht.testing.CombinedSingleTestCase;
import vht.testing.example1.testcase.HttpUtil;

public class Login extends CombinedSingleTestCase<Signup> {
    private final String username;
    private final String password;

    private final JSONObject request = new JSONObject();

    public Login(Signup preCombinedSingleTestCase2, String username1, String password1) {
        super(preCombinedSingleTestCase2);
        this.username = username1;
        this.password = password1;
    }

    public Login(String username1, String password1, String houseNumber1) {
        this(new Signup(username1,  password1, houseNumber1), username1, password1);
    }

    @Override
    public void prepare() throws Exception {
        request
                .put("username", username)
                .put("password", password);
    }

    @Override
    public JSONObject doWork() throws Exception {
        var response = HttpUtil.post2("http://127.0.0.1:8080/api/auth/login", request.toString());

        return new JSONObject(response);
    }

    @Override
    public void valid() throws Exception {
        if (responseObject.get("authorization") != null) return;

        throwException("Cannot access:" + responseObject.get("message"));
    }
}
