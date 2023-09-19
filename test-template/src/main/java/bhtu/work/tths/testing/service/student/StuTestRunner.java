package bhtu.work.tths.testing.service.student;

import bhtu.work.tths.testing.Client;
import bhtu.work.tths.testing.template.TestCaseRunner;

public class StuTestRunner extends TestCaseRunner {

    private final Client client;
    private final boolean runAdd;
    private final boolean runFind;
    private final boolean runGet;
    private final boolean runUpdate;
    private String addingJson;
    private String updatingJson;

    public StuTestRunner(Client client, boolean runAdd, boolean runFind, boolean runGet, boolean runUpdate) {

        this.client = client;
        this.runAdd = runAdd;
        this.runFind = runFind;
        this.runGet = runGet;
        this.runUpdate = runUpdate;
    }

    @Override
    public void makeTestCases() {
        setTestValue();
        if (runAdd) addTestCase(new AddStudentTest(client, addingJson));
        FindStudentTest findTest = null;
        if (runFind){
            findTest = new FindStudentTest(client, "name", "SomeStudent");
            addTestCase(findTest);
        }
        if (runGet) addTestCase(new GetStudentTest(client, "648045a58aba772fe97d6fe4"));
        if (runUpdate) addTestCase(new UpdateStudentTest(client, findTest, "SomeStudent", updatingJson));
    }
    
    private void setTestValue() {
        updatingJson = """
            {
                "name": "SomeStudentUpdated",
                "dateOfBirth": "2003-09-15",
                "school": "Truowng hocj Updated",
                "householdNumber": 1Updated,
                "lastestEvent": {
                        "dateOfEvent": "2023-08-22",
                        "nameOfEvent": "Trung Thu",
                        "achievement": "Gioi",
                        "classStr": 8,
                        "totalExpense": 234578,
                        "prizes": [
                            {
                                "nameOfPrize": "Keo (cai)",
                                "amount": 4
                            },\s
                            {
                                "nameOfPrize": "Gao (kg)",
                                "amount": 8
                            },
                            {
                                "nameOfPrize": "Banh (cai)",
                                "amount": 13
                            }
                        ]
                }
                
            }""";

        addingJson = """
            {
                "name": "SomeStudent",
                "dateOfBirth": "2002-09-15",
                "school": "Truowng hocj",
                "householdNumber": 1,
                "events": [
                    {
                        "dateOfEvent": "2023-08-22",
                        "nameOfEvent": "Trung Thu",
                        "achievement": "Gioi",
                        "classStr": 8,
                        "totalExpense": 234578,
                        "prizes": [
                            {
                                "nameOfPrize": "Keo (cai)",
                                "amount": 3
                            },\s
                            {
                                "nameOfPrize": "Gao (kg)",
                                "amount": 7
                            },
                            {
                                "nameOfPrize": "Banh (cai)",
                                "amount": 12
                            }
                        ]
                    }
                ]
            }""";
        
    }
}
