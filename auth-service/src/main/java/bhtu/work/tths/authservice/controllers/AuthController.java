package bhtu.work.tths.authservice.controllers;

import bhtu.work.tths.authservice.services.AuthService;
import bhtu.work.tths.authservice.models.dto.LoginRequest;
import bhtu.work.tths.authservice.models.dto.SignupRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(originPatterns = "*", maxAge = 3600)
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(@NonNull AuthService authService) {
        this.authService = authService;
    }

    // #region mapping
    @PostMapping("login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("User logging in: Username:" + loginRequest.username() + "; Password:" + loginRequest.password());
        return this.authService.authenticateUser(loginRequest);
    }

    @PostMapping("signup")
    public ResponseEntity<?> registerParent(@Valid @RequestBody SignupRequest signUpRequest) {
        var res = this.authService.registerParent(signUpRequest);

        if ("true".equals(res.get("Created")))
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        else
            return ResponseEntity.badRequest().body(res);
    }
    // #endregion
}