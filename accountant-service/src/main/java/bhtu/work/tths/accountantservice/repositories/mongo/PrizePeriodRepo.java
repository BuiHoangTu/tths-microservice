package bhtu.work.tths.accountantservice.repositories.mongo;

import java.time.LocalDate;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import bhtu.work.tths.accountantservice.models.PrizePeriod;

public interface PrizePeriodRepo extends MongoRepository<PrizePeriod, LocalDate> {

    @Aggregation(pipeline = {
            "{ '$match': { 'dateOfApply' : {$lte : ?0 }} }",
            "{ '$sort' : { 'dateOfApply' : -1 } }",
            "{ '$limit' : 1 }"
    })
    PrizePeriod findByDateOfApply(LocalDate dateOfApply);
}
