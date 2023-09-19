/*
 * Copyright 2021 ducvv5.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package vht.testing;

import java.util.ArrayList;

/**
 * This thing do : tests.forEach::test()
 */
public abstract class TestCaseRunner {

    protected final ArrayList<SingleTestCase> testCases;

    public TestCaseRunner() {
        testCases = new ArrayList<>();
    }

    public ArrayList<SingleTestCase> getTestCases() {
        return testCases;
    }

    public void addTestCase(SingleTestCase testCase) {
        testCases.add(testCase);
    }

    public void addNegativeTestCase(SingleTestCase testCase) {
        testCases.add(new NegativeTestCase(testCase));
    }

    public void runTestCase() throws Exception {

        for (SingleTestCase testCase : testCases) {
            testCase.test();
        }
    }

    public abstract void makeTestCases();

    /**
     * makeTestCases() and runTestCase();
     * @throws Exception
     */
    public void test() throws Exception {
        makeTestCases();
        runTestCase();
    }
}
