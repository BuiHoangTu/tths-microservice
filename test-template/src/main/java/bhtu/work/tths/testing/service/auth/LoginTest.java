package bhtu.work.tths.testing.service.auth;

import bhtu.work.tths.testing.Client;
import bhtu.work.tths.testing.template.DependentTestCase;
import org.json.JSONObject;
import vht.testing.example1.testcase.HttpUtil;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class LoginTest extends DependentTestCase {
    private final Client client;

    private final JSONObject request = new JSONObject();

    public LoginTest(SignupTest preCombinedSingleTestCase2, Client client) {
        super(emptyList());
        if (preCombinedSingleTestCase2 != null) this.preCombinedSingleTestCases.add(preCombinedSingleTestCase2);
        this.client = client;
    }

    public LoginTest(Client client) {
        this(new SignupTest(client), client);
    }

    @Override
    public void prepare() throws Exception {
        request
                .put("username", client.username)
                .put("password", client.password);
    }

    @Override
    public JSONObject doWork() throws Exception {
        var response = HttpUtil.post2( client.baseUrl + "/api/auth/login", request.toString());

        return new JSONObject(response);
    }

    @Override
    public void valid() throws Exception {
        client.jwt = responseObject.get("authorization").toString();
        if (client.jwt == null) throw new Exception("Cannot access:" + responseObject.get("message"));
    }
}
