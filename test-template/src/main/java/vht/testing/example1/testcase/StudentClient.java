package vht.testing.example1.testcase;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
@Slf4j
public class StudentClient {

    public StudentClient(String user2, String password2) {
        user = user2;
        password = password2;
    }

    private String user;
    private String password;

    private String token;

    public JSONObject login() throws Exception {
        JSONObject loginJson = new JSONObject();
        loginJson.put("username", user);
        loginJson.put("password", password);

        String response = HttpUtil.post2("http://127.0.0.1:8080/api/auth/login", loginJson.toString());

        token = "get token";
        return new JSONObject(response);
    }


    public JSONObject signup() throws Exception {
        JSONObject postObject = new JSONObject();
        postObject.put("username", user);
        postObject.put("password", password);
        postObject.put("householdNumber", "1212121124");
        String response = HttpUtil.post2("http://127.0.0.1:8080/api/auth/signup", postObject.toString());
        //log.info("signup response:" + response);
        return new JSONObject(response);
    }
}
