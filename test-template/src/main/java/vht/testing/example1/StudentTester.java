package vht.testing.example1;

import lombok.extern.slf4j.Slf4j;
import vht.testing.SingleTestCase;
import vht.testing.TestCaseRunner;
import vht.testing.example1.testcase.RegisterTestCase;
import vht.testing.example1.testcase.StudentClient;
import vht.testing.example1.testcase.LoginTestCase;

@Slf4j
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
        log.info("num of test case:" + studentTester.getTestCases().size());

        studentTester.test();


    }
}
