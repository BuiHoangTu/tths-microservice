package bhtu.work.tths.accountantservice.services;

import bhtu.work.tths.core.models.AwardPeriod;
import bhtu.work.tths.accountantservice.repositories.mongo.AwardPeriodRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class AwardPeriodService {
    private static final Logger APS_LOGGER = LoggerFactory.getLogger(AwardPeriodService.class);

    private final AwardPeriodRepo awardPeriodRepo;

    @Autowired
    public AwardPeriodService(AwardPeriodRepo awardPeriodRepo) {
        this.awardPeriodRepo = awardPeriodRepo;
    }

    public AwardPeriod getAwardPeriod(String dateString) {
        LocalDate dateOfApply;
        if (dateString != null) {
            try {
                dateOfApply = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE_TIME);
            } catch (DateTimeParseException _e) {
                APS_LOGGER.info("Can't parse date, choosing now.", _e);
                dateOfApply = LocalDate.now();
            }
        } else {
            dateOfApply = LocalDate.now();
        }

        return this.awardPeriodRepo.findByDateOfApply(dateOfApply);
    }

    public AwardPeriod updateAwardLevel(AwardPeriod awardPeriod) {
        if (awardPeriod.getDateOfApply() == null)
            awardPeriod.setDateOfApply(LocalDate.now());
        return this.awardPeriodRepo.insert(awardPeriod);
    }

}
