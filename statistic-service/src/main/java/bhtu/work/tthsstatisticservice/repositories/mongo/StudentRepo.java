package bhtu.work.tthsstatisticservice.repositories.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import bhtu.work.tthsstatisticservice.models.Student;

public interface StudentRepo extends MongoRepository<Student, String> {

    List<Student> findByIdRegex(String id);

    List<Student> findByNameRegex(String name);

    List<Student> findByParentRegex(String parent);

    List<Student> findBySchoolRegex(String string);
}
