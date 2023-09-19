package bhtu.work.tths.testing.service.auth;

import bhtu.work.tths.testing.Client;
import bhtu.work.tths.testing.template.TestCaseRunner;

public class AuthTestRunner extends TestCaseRunner {
    private final Client client;
    private final boolean runSignup;
    private final boolean runLogin;

    public AuthTestRunner(Client client, boolean runSignup, boolean runLogin) {
        this.client = client;
        this.runSignup = runSignup;
        this.runLogin = runLogin;
    }

    @Override
    public void makeTestCases() {
        SignupTest signup = null;
        if(runSignup) signup = new SignupTest(client);
        var login = new LoginTest(signup, client);

        if(runSignup) this.addTestCase(signup);
        if(runLogin) this.addTestCase(login);
    }
}
