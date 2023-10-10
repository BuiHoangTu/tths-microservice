/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bhtu.work.tths.testing.template;

import org.json.JSONObject;

/**
 * This thing test(): if not haveRun, prepare -> doWork and save Response -> valid return code and run defining valid
 * @author d
 */
public abstract class SingleTestCase {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SingleTestCase.class);

    public static String defaultExpectedResultField = "result_code";
    public static String defaultExpectedResultCode = "0";

    public SingleTestCase() {
        this(defaultExpectedResultCode);
    }

    public SingleTestCase(String expectedResultCode2) {
        this(defaultExpectedResultField, expectedResultCode2);
    }

    public SingleTestCase(String expectedResultField2, String expectedResultCode2) {
        expectedResultCode = expectedResultCode2;
        expectedResultField = expectedResultField2;
        testCaseId =  getMethodNameLineNumber("make");
    }


    public static String getMethodNameLineNumber() {
        try {
            final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
            return ste[2].getMethodName() + ":" + ste[2].getLineNumber();
        } catch (Exception e) {
            //dtr.S_ystem.printStackTree(e);
            return "";
        }
    }

    public static String getMethodNameLineNumber(String funcPreDict) {
        try {
            final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
            for (StackTraceElement stackTraceElement : ste) {
                if (stackTraceElement.getMethodName().contains(funcPreDict)) {
                    return stackTraceElement.getMethodName() + ":" + stackTraceElement.getLineNumber();
                }
            }

        } catch (Exception e) {
            //dtr.S_ystem.printStackTree(e);
            return "";
        }
        return "";
    }

    public JSONObject responseObject;
    public String expectedResultCode = "0";
    public String expectedResultField = "result_code";
    public String testCaseId;

    private boolean runed = false;

    public abstract void prepare() throws Exception;

    public abstract JSONObject doWork() throws Exception;

    public void validResultCode() throws Exception {
        if (expectedResultField != null && expectedResultField.isEmpty() == false
                && expectedResultCode != null && expectedResultCode.isEmpty() == false
                && responseObject.get(expectedResultField).toString().equals(expectedResultCode) == false) {
            throw new Exception("expectedResultField or expectedResultCode not valid");
        }
    }

    public abstract void valid() throws Exception;

    public void test() throws Exception {
        if (runed == false) {
            runed = true;

            LOGGER.info("-------------------- run test case:" + testCaseId);

            prepare();
            responseObject = doWork();
            if (responseObject != null) {
                LOGGER.info("responseObject:" + responseObject.toString(4));
                validResultCode();
                valid();
            }
        }
    }

    public void throwException(String message) throws Exception {
        throw new Exception(message);
    }

}
