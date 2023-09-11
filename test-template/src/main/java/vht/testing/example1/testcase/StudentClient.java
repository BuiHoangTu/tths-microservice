package vht.testing.example1.testcase;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.json.JSONObject;

public class StudentClient {

    public StudentClient(String user2, String password2) {
        user = user2;
        password = password2;
    }

    private String user;
    private String password;


    public JSONObject login() throws Exception {
        JSONObject loginJson = new JSONObject();
        loginJson.put("username", user);
        loginJson.put("password", password);

        String response = HttpUtil.post2("http://127.0.0.1:8080/api/auth/login", loginJson.toString());

        return new JSONObject(response);
    }





}
