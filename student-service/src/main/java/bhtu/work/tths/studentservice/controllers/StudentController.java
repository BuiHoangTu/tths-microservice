package bhtu.work.tths.studentservice.controllers;

import bhtu.work.tths.studentservice.services.grpc.clients.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import bhtu.work.tths.studentservice.models.Student;
import bhtu.work.tths.studentservice.models.dto.StudentOneReward;
import bhtu.work.tths.studentservice.services.StudentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(path = "api/student")
public class StudentController {
    private final StudentService studentService;
    private final Auth auth;

    @Autowired
    public StudentController(StudentService studentService, Auth auth) {
        this.studentService = studentService;
        this.auth = auth;
    }

    // #region mapping
//    @PreAuthorize("authentication.authorities.contains('READ_NATIONAL_STUDENTS')")
    @GetMapping("get")
    public ResponseEntity<Student> getStudent(
            HttpServletRequest request,
            @RequestParam @NonNull String id) {
        String jwt = Objects.requireNonNull(request.getHeaders(HttpHeaders.AUTHORIZATION)).nextElement(); // get first jwt
        var verifications = auth.authorize(jwt);
        if (!verifications.getIsValid() || !verifications.getAuthoritiesList().contains(""/*Todo: add things*/)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().body(studentService.getStudentById(id));
    }

    @GetMapping("find")
    public ResponseEntity<List<Student>> findStudent(
            HttpServletRequest request,
            @RequestParam(name = "category", defaultValue = "ID") String categoryStr,
            @RequestParam String filter) {
        String jwt = Objects.requireNonNull(request.getHeaders(HttpHeaders.AUTHORIZATION)).nextElement(); // get first jwt
        var verifications = auth.authorize(jwt);
        if (!verifications.getIsValid() || !verifications.getAuthoritiesList().contains(""/*Todo: add things*/)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().body(studentService.findStudent(categoryStr, filter));

    }

    @PostMapping("add")
    public ResponseEntity<?> addStudent(
            HttpServletRequest request,
            @RequestBody Student idlessHocSinh) {
        String jwt = Objects.requireNonNull(request.getHeaders(HttpHeaders.AUTHORIZATION)).nextElement(); // get first jwt
        var verifications = auth.authorize(jwt);
        if (!verifications.getIsValid() || !verifications.getAuthoritiesList().contains(""/*Todo: add things*/)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        studentService.addStudent(idlessHocSinh);
        Map<String, Boolean> response = Map.of("isAdded", true);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateStudent(
            HttpServletRequest request,
            @RequestBody StudentOneReward changedStudent) {
        String jwt = Objects.requireNonNull(request.getHeaders(HttpHeaders.AUTHORIZATION)).nextElement(); // get first jwt
        var verifications = auth.authorize(jwt);
        if (!verifications.getIsValid() || !verifications.getAuthoritiesList().contains(""/*Todo: add things*/)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        studentService.updateStudent(changedStudent);
        Map<String, Boolean> response = Map.of("isUpdated", true);
        return ResponseEntity.ok().body(response);
    }
    // #endregion
}
