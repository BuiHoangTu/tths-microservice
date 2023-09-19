package bhtu.work.tths.testing;

import bhtu.work.tths.testing.service.accountant.AccTestRunner;
import bhtu.work.tths.testing.service.auth.AuthTestRunner;
import bhtu.work.tths.testing.service.statistic.StaTestRunner;
import bhtu.work.tths.testing.service.student.StuTestRunner;
import bhtu.work.tths.testing.template.SingleTestCase;

public class Main {
    static {
        SingleTestCase.defaultExpectedResultField = "";
        SingleTestCase.defaultExpectedResultCode = "";
    }


    public static void main(String[] args) throws Exception {
        // test as new account
        var parentAccount = new Client();
        parentAccount.username = "test1";
        parentAccount.password = "test1";
        parentAccount.houseNumber = "test1";
        new AuthTestRunner(parentAccount, true, true).test();
        new AccTestRunner(parentAccount, false, true, false, true).test(); // dont run update (no authority)
        // no staticTest
        new StuTestRunner(parentAccount, false, false, true, false).test(); // can only get

        // test as admin
        var adminAccount = new Client();
        adminAccount.username = "admin";
        adminAccount.password = "admin2";
        // use admin account, not running signup
        new AuthTestRunner(adminAccount, false, true).test();
        new AccTestRunner(adminAccount, true, true, true, true).test();
        new StaTestRunner(adminAccount, true, true).test();
        new StuTestRunner(adminAccount, true, true, true, true).test();

        // test other restriction
        boolean hasException = false;
        try {
            new AccTestRunner(parentAccount, true, false, false, false).test();
        } catch (Exception ignored) {
            hasException = true;
        }
        if (!hasException) throw new Exception("Over authorization");
        hasException = false;
        try {
            new AccTestRunner(parentAccount, false, false, true, false).test();
        } catch (Exception ignored) {
            hasException = true;
        }
        if (!hasException) throw new Exception("Over authorization");

        hasException = false;
        try {
            new StaTestRunner(parentAccount, true, false).test();
        } catch (Exception ignored) {
            hasException = true;
        }
        if (!hasException) throw new Exception("Over authorization");
        hasException = false;
        try {
            new StaTestRunner(parentAccount, false, true).test();
        } catch (Exception ignored) {
            hasException = true;
        }
        if (!hasException) throw new Exception("Over authorization");

        hasException = false;
        try {
            new StuTestRunner(parentAccount, true, false, false, false).test();
        } catch (Exception ignored) {
            hasException = true;
        }
        if (!hasException) throw new Exception("Over authorization");
        hasException = false;
        try {
            new StuTestRunner(parentAccount, false, true, false, false).test();
        } catch (Exception ignored) {
            hasException = true;
        }
        if (!hasException) throw new Exception("Over authorization");
        hasException = false;
        try {
            new StuTestRunner(parentAccount, false, false, false, true).test();
        } catch (Exception ignored) {
            hasException = true;
        }
        if (!hasException) throw new Exception("Over authorization");

    }
}
