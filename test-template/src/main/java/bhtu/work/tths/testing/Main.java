package bhtu.work.tths.testing;

import bhtu.work.tths.testing.service.accountant.AccTest;
import vht.testing.SingleTestCase;

public class Main {
    static {
        SingleTestCase.defaultExpectedResultField = "";
        SingleTestCase.defaultExpectedResultCode = "";
    }


    public static void main(String[] args) throws Exception {
//        new AuthTest().test();

        new AccTest().test();
    }
}
