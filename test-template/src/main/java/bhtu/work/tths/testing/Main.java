package bhtu.work.tths.testing;

import bhtu.work.tths.testing.service.accountant.AccTest;
import bhtu.work.tths.testing.service.auth.AuthTest;
import bhtu.work.tths.testing.service.auth.Login;
import bhtu.work.tths.testing.service.auth.Signup;
import vht.testing.SingleTestCase;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {
    static {
        SingleTestCase.defaultExpectedResultField = "";
        SingleTestCase.defaultExpectedResultCode = "";
    }

    private static final String username = "test_user5";
    private static final String password = "test_pwd";
    private static final String houseNumber = "test_number5";


    public static void main(String[] args) throws Exception {
//        new AuthTest(username, password, houseNumber).test();

        List<Map<String, ?>> awardLevels = new LinkedList<>();
        var awardLevel = new HashMap<String, Object>(2);
        awardLevel.put("achievement", "gioi");
        awardLevel.put("prizeValue", 1000);
        awardLevels.add(awardLevel);
        awardLevel = new HashMap<>(2);
        awardLevel.put("achievement", "tot");
        awardLevel.put("prizeValue", 10);
        awardLevels.add(awardLevel);
        new AccTest(awardLevels).test();
    }
}
