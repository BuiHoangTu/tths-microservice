package bhtu.work.tths.studentservice.repositories.mongo;

import java.util.List;

import bhtu.work.tths.studentservice.models.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepo extends MongoRepository<Student, String> {

    List<Student> findByIdRegex(String id);

    List<Student> findByNameRegex(String name);

    List<Student> findByParentRegex(String parent);

    List<Student> findBySchoolRegex(String string);

    List<Student> findByHouseholdNumber(String householdNumber);
}
