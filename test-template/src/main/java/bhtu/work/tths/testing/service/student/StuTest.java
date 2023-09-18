package bhtu.work.tths.testing.service.student;

import vht.testing.TestCaseRunner;

public class StuTest extends TestCaseRunner {
    @Override
    public void makeTestCases() {
        final var addingJson = """
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
//        addTestCase(new Add(addingJson));
        addTestCase(new Find("name", "SomeStudent"));
        addTestCase(new Get("648045a58aba772fe97d6fe4"));

        final String updatingJson = """
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
        addTestCase(new Update("SomeStudent", updatingJson));
    }
}
