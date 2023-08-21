package bhtu.work.tths.accountantservice.services;

import bhtu.work.tths.accountantservice.models.AwardPeriod;
import bhtu.work.tths.accountantservice.repositories.mongo.AwardPeriodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
public class AwardPeriodService {
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

        return this.awardPeriodRepo.findByDateOfApply(dateOfApply);
    }

    public AwardPeriod updateAwardLevel(AwardPeriod awardPeriod) {
        if (awardPeriod.getDateOfApply() == null)
            awardPeriod.setDateOfApply(LocalDate.now());
        return this.awardPeriodRepo.insert(awardPeriod);
    }

}
