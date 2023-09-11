package vht.testing.example1.testcase;

import org.json.JSONObject;
import vht.testing.SingleTestCase;

public class RegisterTestCase extends SingleTestCase {
    private StudentClient studentClient;

    public RegisterTestCase(StudentClient studentClient2) {
        studentClient = studentClient2;
    }

    @Override
    public void prepair() throws Exception {

    }

    @Override
    public JSONObject doWork() throws Exception {
        return null;
    }

    @Override
    public void valid() throws Exception {

    }

    public StudentClient getStudentClient() {
        return studentClient;
    }
}
