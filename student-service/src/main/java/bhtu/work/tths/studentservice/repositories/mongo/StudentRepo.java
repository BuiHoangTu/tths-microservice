package bhtu.work.tths.studentservice.repositories.mongo;

import bhtu.work.tths.studentservice.models.EventOfStudent;
import bhtu.work.tths.studentservice.models.PrizeGroup;
import bhtu.work.tths.studentservice.models.Student;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface StudentRepo extends MongoRepository<Student, String> {

    List<Student> findByIdRegex(String id);

    List<Student> findByNameRegex(String name);

    List<Student> findByParentRegex(String parent);

    List<Student> findBySchoolRegex(String string);

    List<Student> findByHouseholdNumber(String householdNumber);

    @Aggregation(pipeline = {
            "{'$project': { _id:  0, events:  1} }", // get only events field
            "{'$unwind':  '$events'}", // un-group events into many one-event
            "{'$match':  {'_id': ?0}}", // event_id : date == first argument
    })
    List<EventOfStudent> findPrizeGroupByEvents_DateOfEvent(LocalDate dateOfEvent);

    @Aggregation(pipeline = {
            "{'$project': { _id:  0, events:  1} }", // get only events field
            "{'$unwind':  '$events'}", // un-group events into many one-event
            "{'$match':  {'nameOfEvent': ?0}}", // event_id : date == first argument
    })
    List<EventOfStudent> findPrizeGroupByEvents_NameOfEvent(String name);
}
