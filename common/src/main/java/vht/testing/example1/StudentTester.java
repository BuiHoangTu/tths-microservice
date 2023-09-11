package vht.testing.example1;

import vht.testing.SingleTestCase;
import vht.testing.TestCaseRunner;
import vht.testing.example1.testcase.LoginTestCase;
import vht.testing.example1.testcase.RegisterTestCase;
import vht.testing.example1.testcase.StudentClient;

public class StudentTester extends TestCaseRunner {
    static  {
        SingleTestCase.defaultExpectedResultCode = "";
        SingleTestCase.defaultExpectedResultField = "";
    }
    @Override
    public void makeTestCases() {
        StudentClient studentClient  = new StudentClient("ducvv", "121212");
        RegisterTestCase registerTestCase = new RegisterTestCase(studentClient);
        addTestCase(registerTestCase);

        LoginTestCase loginTestCase = new LoginTestCase(registerTestCase);
        addTestCase(loginTestCase);
    }

    public static void main(String[] args) throws Exception {
        StudentTester studentTester = new StudentTester();
        studentTester.makeTestCases();
        studentTester.test();
    }
}
