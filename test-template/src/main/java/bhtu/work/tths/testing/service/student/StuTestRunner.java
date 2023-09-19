package bhtu.work.tths.testing.service.student;

import bhtu.work.tths.testing.Client;
import bhtu.work.tths.testing.template.TestCaseRunner;

public class StuTestRunner extends TestCaseRunner {

    private final Client client;
    private String addingJson;
    private String updatingJson;

    public StuTestRunner(Client client) {

        this.client = client;
    }

    @Override
    public void makeTestCases() {
        setTestValue();
        addTestCase(new AddStudentTest(client, addingJson));
        FindStudentTest findTest = new FindStudentTest(client, "name", "SomeStudent");
        addTestCase(findTest);
        addTestCase(new GetStudentTest(client, "648045a58aba772fe97d6fe4"));
        addTestCase(new UpdateStudentTest(client, findTest, "SomeStudent", updatingJson));
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
