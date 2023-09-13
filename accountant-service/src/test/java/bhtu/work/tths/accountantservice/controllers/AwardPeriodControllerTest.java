package bhtu.work.tths.accountantservice.controllers;

import bhtu.work.tths.accountantservice.models.AwardLevel;
import bhtu.work.tths.accountantservice.models.AwardPeriod;
import bhtu.work.tths.accountantservice.repositories.mongo.AwardPeriodRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.LinkedList;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AwardPeriodControllerTest {
    @MockBean
    private AwardPeriodRepo repo;
    @Autowired
    private AwardPeriodController awardPeriodController;
    private AwardPeriod testAP;

    @BeforeAll
    public void prepare() throws Exception {
        testAP = new AwardPeriod();

        var testDate = LocalDate.of(1991, 1, 1);
        testAP.setDateOfApply(testDate);

        var list = new LinkedList<AwardLevel>();
        var testAL = new AwardLevel();
        testAL.setAchievement("AclieveMentr");
        testAL.setPrizeValue(12121211);
        list.add(testAL);

        testAL = new AwardLevel();
        testAL.setAchievement("not achievement");
        testAL.setPrizeValue(10);
        list.add(testAL);

        testAP.setAwardLevels(list);

        Mockito.when(repo.findByDateOfApply(Mockito.any())).thenReturn(testAP);
    }

    @Test
    void getAwardPeriod() throws Exception {
        // unit test
//        var res = awardPeriodController.getAwardPeriod(null);
//        Assertions.assertEquals(testAP, res.getBody());
    }

    @Test
    void updateAwardLevel() {
    }
}