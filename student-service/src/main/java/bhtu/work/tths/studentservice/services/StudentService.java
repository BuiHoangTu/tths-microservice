package bhtu.work.tths.studentservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import bhtu.work.tths.studentservice.models.EventOfStudent;
import bhtu.work.tths.studentservice.models.Student;
import bhtu.work.tths.studentservice.models.dto.StudentOneReward;
import bhtu.work.tths.studentservice.models.enums.EGetStudents;
import bhtu.work.tths.studentservice.repositories.mongo.StudentRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StudentService {
    private final StudentRepo studentRepo;

    @Autowired
    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public List<Student> findStudent(String categoryStr, String filter) {

        EGetStudents category = EGetStudents.valueOf(categoryStr.toUpperCase());

        switch (category) {
            case ID -> {
                return this.studentRepo.findByIdRegex(filter + ".*");
            }
            case NAME -> {
                return this.studentRepo.findByNameRegex(".*" + filter + ".*");
            }
            case SCHOOL -> {
                return this.studentRepo.findBySchoolRegex(".*" + filter + ".*");
            }
            case PARENT -> {
                return this.studentRepo.findByParentRegex(".*" + filter + ".*");
            }
            default -> throw new IllegalArgumentException();
        }

    }

    public Student addStudent(Student student) {
        var optionalStudent = studentRepo.findStudentByHouseholdNumberAndName(student.getHouseholdNumber(), student.getName());
        if (optionalStudent.isPresent()) {
            var existingStudent = optionalStudent.get();
            existingStudent.getEvents().addAll(student.getEvents());
            return studentRepo.save(existingStudent);
        } else return studentRepo.insert(student);
    }

    public Student getStudentById(@NonNull String Id) {
        return studentRepo.findById(Id).orElse(null);
    }

    public Student updateStudent(StudentOneReward changedStudent) {
        EventOfStudent rewardToChange = changedStudent.lastestEvent();
        Student studentToChange = new Student(changedStudent.id(), changedStudent.name(), changedStudent.dateOfBirth(),
                changedStudent.school(), changedStudent.householdNumber(), changedStudent.parent());

        Student onDbStudent = studentRepo.findById(studentToChange.getId()).orElseThrow(() -> new NoSuchElementException("This student's id is not existed"));
        onDbStudent.setHouseholdNumber(studentToChange.getHouseholdNumber());
        onDbStudent.setDateOfBirth(studentToChange.getDateOfBirth());
        onDbStudent.setParent(studentToChange.getParent());
        onDbStudent.setName(studentToChange.getName());
        onDbStudent.setSchool(studentToChange.getSchool());

        var rewardOrNot = onDbStudent.getEvents().stream()
                .filter((reward1) -> reward1.getDateOfEvent().equals(rewardToChange.getDateOfEvent())).findFirst();
        rewardOrNot.ifPresent(eventOfStudent -> onDbStudent.getEvents().remove(eventOfStudent));
        onDbStudent.getEvents().add(rewardToChange);

        return studentRepo.save(onDbStudent);
    }

    public List<EventOfStudent> dsds() {
        var student = studentRepo.findEventsByName("Trung Thu");

        return new ArrayList<>(student.getEvents());
    }
}
