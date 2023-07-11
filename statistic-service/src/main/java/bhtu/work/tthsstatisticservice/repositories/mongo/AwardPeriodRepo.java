package bhtu.work.tthsstatisticservice.repositories.mongo;

import java.time.LocalDate;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import bhtu.work.tthsstatisticservice.models.AwardPeriod;

public interface AwardPeriodRepo extends MongoRepository<AwardPeriod, LocalDate> {

    @Aggregation(pipeline = {
            "{ '$match': { 'dateOfApply' : {$lt : ?0 }} }",
            "{ '$sort' : { 'dateOfApply' : -1 } }",
            "{ '$limit' : 1 }"
    })
    AwardPeriod findByDateOfApply(LocalDate dateOfApply);

}
