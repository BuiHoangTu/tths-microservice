package vht.testing.example1.testcase;

import org.json.JSONObject;
import vht.testing.SingleTestCase;

public class RegisterTestCase extends SingleTestCase {
    private StudentClient studentClient;

    public RegisterTestCase(StudentClient studentClient2) {
        studentClient = studentClient2;
    }

    @Override
    public void prepare() throws Exception {

    }

    @Override
    public JSONObject doWork() throws Exception {
        return getStudentClient().signup();
    }

    @Override
    public void valid() throws Exception {
//        if (responseObject.getInt("status") != 200 ) {
//            throw new Exception("status not 200");
//        }
        if (responseObject.has("Error") || responseObject.has("error")) {
            throw new Exception("contain Error field => Failed");
        }
    }

    public StudentClient getStudentClient() {
        return studentClient;
    }
}
