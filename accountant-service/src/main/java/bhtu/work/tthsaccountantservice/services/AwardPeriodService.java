package bhtu.work.tthsaccountantservice.services;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bhtu.work.tthsaccountantservice.models.AwardPeriod;
import bhtu.work.tthsaccountantservice.repositories.mongo.AwardPeriodRepo;

@Service
public class AwardPeriodService {
    private static final Logger logger = LoggerFactory.getLogger(AwardPeriodService.class);

    private final AwardPeriodRepo awardPeriodRepo;

    @Autowired
    public AwardPeriodService(AwardPeriodRepo awardPeriodRepo) {
        this.awardPeriodRepo = awardPeriodRepo;
    }

    public AwardPeriod getAwardPeriod(String dateString) {
        LocalDate dateOfApply;
        if (dateString != null) {
            try {
                dateOfApply = LocalDate.parse(dateString);
            } catch (DateTimeParseException _e) {
                dateOfApply = LocalDate.now();
            }
        } else {
            dateOfApply = LocalDate.now();
        }

        logger.debug("Got:" + dateString + ". Parsed:" + dateOfApply);

        return this.awardPeriodRepo.findByDateOfApply(dateOfApply);
    }

    public AwardPeriod updateAwardLevel(AwardPeriod awardPeriod) {
        if (awardPeriod.getDateOfApply() == null)
            awardPeriod.setDateOfApply(LocalDate.now());
        return this.awardPeriodRepo.insert(awardPeriod);
    }

}
