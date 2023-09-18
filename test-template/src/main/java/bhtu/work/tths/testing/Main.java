package bhtu.work.tths.testing;

import bhtu.work.tths.testing.service.accountant.AccTest;
import bhtu.work.tths.testing.service.auth.AuthTest;
import bhtu.work.tths.testing.service.statistic.StaTest;
import bhtu.work.tths.testing.service.student.StuTest;
import vht.testing.SingleTestCase;

public class Main {
    static {
        SingleTestCase.defaultExpectedResultField = "";
        SingleTestCase.defaultExpectedResultCode = "";
    }


    public static void main(String[] args) throws Exception {
        new AuthTest().test();

        new AccTest().test();

        new StaTest().test();

        new StuTest().test();
    }
}
