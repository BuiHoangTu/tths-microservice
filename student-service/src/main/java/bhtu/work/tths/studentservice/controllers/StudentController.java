package bhtu.work.tths.studentservice.controllers;

import bhtu.work.tths.share.utils.Authorizing;
import bhtu.work.tths.studentservice.services.grpc.clients.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Set;

@RestController
@RequestMapping(path = "api/student")
public class StudentController {
    private static final Logger STUDENT_CONTROLLER_LOGGER = LoggerFactory.getLogger(StudentController.class);
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
            @RequestParam @NonNull String id
    ) {
        final Set<Integer> VALID_ACCESS_CODES = Set.of(10, 11, 30, 20);

        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwt != null) {
            var verifications = auth.authorize(jwt);

            if (verifications.getIsValid()) {
                List<String> authorities = verifications.getAuthoritiesList();
                // authority match will return non-null
                var res = Authorizing.matchAuthorities(
                        authorities,
                        VALID_ACCESS_CODES,
                        () -> ResponseEntity.ok().body(studentService.getStudentById(id)),
                        null,
                        STUDENT_CONTROLLER_LOGGER::error
                );
                if (res != null) return res;
            }
        }
        return ResponseEntity.ok().body(studentService.getStudentById(id));
    }

    @GetMapping("find")
    public ResponseEntity<?> findStudent(
            HttpServletRequest request,
            @RequestParam(name = "category", defaultValue = "ID") String categoryStr,
            @RequestParam String filter
    ) {
        final Set<Integer> VALID_ACCESS_CODES = Set.of(20, 30);

        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwt != null) {
            var verifications = auth.authorize(jwt);
            // must be valid jwt
            if (verifications.getIsValid()) {
                List<String> authorities = verifications.getAuthoritiesList();
                // authority match will return non-null
                var res = Authorizing.matchAuthorities(
                        authorities,
                        VALID_ACCESS_CODES,
                        () -> {
                            try {
                                return ResponseEntity.ok().body(studentService.findStudent(categoryStr, filter));
                            } catch (IllegalArgumentException e) {
                                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
                            }
                        },
                        null,
                        STUDENT_CONTROLLER_LOGGER::error
                );
                if (res != null) return res;
            }
        }
        // if not, user not authorized
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("add")
    public ResponseEntity<?> addStudent(
            HttpServletRequest request,
            @RequestBody Student idLessHocSinh
    ) {
        final Set<Integer> VALID_ACCESS_CODES = Set.of(23, 32);

        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwt != null) {
            var verifications = auth.authorize(jwt);

            // must be valid jwt
            if (verifications.getIsValid()) {
                List<String> authorities = verifications.getAuthoritiesList();
                // authority match will return non-null
                var res = Authorizing.matchAuthorities(
                        authorities,
                        VALID_ACCESS_CODES,
                        () -> {
                            studentService.addStudent(idLessHocSinh);
                            Map<String, Boolean> response = Map.of("isAdded", true);
                            return ResponseEntity.ok().body(response);                    },
                        null,
                        STUDENT_CONTROLLER_LOGGER::error
                );
                if (res != null) return res;
            }
        }
        // if not, user not authorized
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("update")
    public ResponseEntity<?> updateStudent(
            HttpServletRequest request,
            @RequestBody StudentOneReward changedStudent
    ) {
        final Set<Integer> VALID_ACCESS_CODES = Set.of(23, 32);

        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwt != null) {
            var verifications = auth.authorize(jwt);

            // must be valid jwt
            if (verifications.getIsValid()) {
                List<String> authorities = verifications.getAuthoritiesList();
                // authority match will return non-null
                var res = Authorizing.matchAuthorities(
                        authorities,
                        VALID_ACCESS_CODES,
                        () -> {
                            studentService.updateStudent(changedStudent);
                            Map<String, Boolean> response = Map.of("isUpdated", true);
                            return ResponseEntity.ok().body(response);
                        },
                        null,
                        STUDENT_CONTROLLER_LOGGER::error
                );
                if (res != null) return res;
            }
        }
        // if not, user not authorized
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    // #endregion
}
