package bhtu.work.tthsstudentservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import bhtu.work.tthsstudentservice.models.Student;
import bhtu.work.tthsstudentservice.models.dto.StudentOneReward;
import bhtu.work.tthsstudentservice.services.StudentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // #region mapping
    @PreAuthorize("authentication.authorities.contains('READ_NATIONAL_STUDENTS')")
    @GetMapping("get")
    public ResponseEntity<Student> getStudent(@RequestParam @NonNull String id) {
        return ResponseEntity.ok().body(studentService.getStudentById(id));
    }

    @GetMapping("find")
    public ResponseEntity<List<Student>> findStudent(
            @RequestParam(name = "category", defaultValue = "ID") String categoryStr,
            @RequestParam String filter) {

        return ResponseEntity.ok().body(studentService.findStudent(categoryStr, filter));

    }

    @PostMapping("add")
    public ResponseEntity<?> addStudent(@RequestBody Student idlessHocSinh) {
        studentService.addStudent(idlessHocSinh);
        Map<String, Boolean> response = Map.of("isAdded", true);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateStudent(@RequestBody StudentOneReward changedStudent) {
        studentService.updateStudent(changedStudent);
        Map<String, Boolean> response = Map.of("isUpdated", true);
        return ResponseEntity.ok().body(response);
    }
    // #endregion
}
