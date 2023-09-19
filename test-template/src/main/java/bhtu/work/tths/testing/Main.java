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
        var client = new Client();
        client.username = "test1";
        client.password = "test1";
        client.houseNumber = "test1";

        // new AuthTestRunner(client, true, true).test();

        client = new Client();
        client.username = "admin";
        client.password = "admin2";
        new AuthTestRunner(client, false, true).test();
        new AccTestRunner(client).test();
        new StaTestRunner(client).test();
        new StuTestRunner(client).test();
    }
}
