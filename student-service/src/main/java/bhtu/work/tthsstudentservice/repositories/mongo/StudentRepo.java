package bhtu.work.tthsstudentservice.repositories.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import bhtu.work.tthsstudentservice.models.Student;

public interface StudentRepo extends MongoRepository<Student, String> {

    List<Student> findByIdRegex(String id);

    List<Student> findByNameRegex(String name);

    List<Student> findByParentRegex(String parent);

    List<Student> findBySchoolRegex(String string);

    Student findDistinctFirstByHouseholdNumber(String householdNumber);
}
