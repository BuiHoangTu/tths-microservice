package bhtu.work.tths.testing.service.auth;

import bhtu.work.tths.testing.Client;
import bhtu.work.tths.testing.template.SingleTestCase;
import org.json.JSONObject;
import vht.testing.example1.testcase.HttpUtil;

public class SignupTest extends SingleTestCase {
    private final JSONObject request = new JSONObject();

    private final Client client;


    public SignupTest(Client client) {
        this.client = client;
    }

    @Override
    public void prepare() throws Exception {
        request
                .put("username", client. username)
                .put("password",client. password)
                .put("householdNumber",client. houseNumber);
    }

    @Override
    public JSONObject doWork() throws Exception {
        var response = HttpUtil.post2(client.baseUrl + "/api/auth/signup", request.toString());

        return new JSONObject(response);
    }

    @Override
    public void valid() throws Exception {
        if (responseObject.get("Created").equals("true")) return;

        throw new Exception("Can't create");
    }
}
