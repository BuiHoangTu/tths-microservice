package bhtu.work.tths.testing.service.auth;

import vht.testing.TestCaseRunner;

public class AuthTest extends TestCaseRunner {

    @Override
    public void makeTestCases() {
        String username = "test_user5";
        String pwd = "test_pwd";
        String houseNumber = "test_number5";
        // auto signup created
        this.addTestCase(new Login(username, pwd, houseNumber));
    }
}
