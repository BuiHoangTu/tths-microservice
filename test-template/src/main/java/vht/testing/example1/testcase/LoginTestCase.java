package vht.testing.example1.testcase;

import org.json.JSONObject;
import vht.testing.CombinedSingleTestCase;

public class LoginTestCase extends CombinedSingleTestCase<RegisterTestCase> {


    public LoginTestCase(RegisterTestCase registerTestCase) {
        super(registerTestCase);

    }

    @Override
    public void prepare() throws Exception {

    }

    @Override
    public JSONObject doWork() throws Exception {
        return getStudentClient().login();
    }

    @Override
    public void valid() throws Exception {

    }

    public StudentClient getStudentClient() {

        return getSuperTestCase().getStudentClient();
    }
}
