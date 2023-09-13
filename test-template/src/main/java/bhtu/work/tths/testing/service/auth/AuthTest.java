package bhtu.work.tths.testing.service.auth;

import vht.testing.TestCaseRunner;

public class AuthTest extends TestCaseRunner {
    private final String username;
    private final String pwd;
    private final String houseNumber;

    public AuthTest(String username, String pwd, String houseNumber) {
        this.username = username;
        this.pwd = pwd;
        this.houseNumber = houseNumber;
    }

    @Override
    public void makeTestCases() {
        this.addTestCase(new Login(username, pwd, houseNumber));
    }
}
