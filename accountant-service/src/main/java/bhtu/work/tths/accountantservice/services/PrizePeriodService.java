package bhtu.work.tths.accountantservice.services;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bhtu.work.tths.accountantservice.models.PrizePeriod;
import bhtu.work.tths.accountantservice.repositories.mongo.PrizePeriodRepo;

@Service
public class PrizePeriodService {
    private final PrizePeriodRepo periodRepo;

    @Autowired
    public PrizePeriodService(PrizePeriodRepo periodRepo) {
        this.periodRepo = periodRepo;
    }

    public PrizePeriod getAwardPeriod(String dateString) {
        LocalDate dateOfApply;
        if (dateString == null || !dateString.isEmpty()) {
            dateOfApply = LocalDate.now();
        } else {
            try {
                dateOfApply = LocalDate.parse(dateString);
            } catch (DateTimeParseException _e) {
                dateOfApply = LocalDate.now();
            }
        }
        return this.periodRepo.findByDateOfApply(dateOfApply);
    }

    public PrizePeriod updateAwardLevel(PrizePeriod prizePeriod) {
        if (prizePeriod.getDateOfApply() == null) prizePeriod.setDateOfApply(LocalDate.now());
        return this.periodRepo.insert(prizePeriod);
    }

}
