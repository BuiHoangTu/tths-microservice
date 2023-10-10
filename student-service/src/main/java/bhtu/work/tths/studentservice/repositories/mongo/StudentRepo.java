package bhtu.work.tths.studentservice.repositories.mongo;

import bhtu.work.tths.core.models.Student;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentRepo extends MongoRepository<Student, String> {

    List<Student> findByIdRegex(String id);

    List<Student> findByNameRegex(String name);

    List<Student> findByParentRegex(String parent);

    List<Student> findBySchoolRegex(String string);

    List<Student> findByHouseholdNumber(String householdNumber);
    Optional<Student> findStudentByHouseholdNumberAndName(String householdNumber, String name);

    /**
     * All event are warped in an empty student
     * @param dateOfEvent date of event
     * @return An empty student warping all event that held in the date
     */
    @Aggregation(pipeline = {
            "{'$unwind':  '$events'}", // un-group events into many one-event
            "{'$match':  {'events._id': ?0}}", // event_id : date == first argument
            "{'$group': {'_id': null, 'events': {'$push': '$events'}}}",
            "{'$project': { _id:  0, events:  1} }", // get only events field
    })
    Student findEventsByDate(LocalDate dateOfEvent);

    /**
     * All event are warped in an empty student
     * @param eventName name of event
     * @return An empty student warping all event that have event name
     */
    @Aggregation(pipeline = {
            "{'$unwind':  '$events'}", // un-group events into many one-event
            "{'$match':  {'events.nameOfEvent': ?0}}", // event_id : date == first argument
            "{'$group': {'_id': null, 'events': {'$push': '$events'}}}",
            "{'$project': { _id:  0, events:  1} }" // get only events field
    })
    Student findEventsByName(String eventName);
}
