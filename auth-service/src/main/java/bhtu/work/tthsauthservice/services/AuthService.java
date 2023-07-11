package bhtu.work.tthsauthservice.services;

import bhtu.work.tthsauthservice.security.jwt.IJwtService;
import bhtu.work.tthsauthservice.security.services.MyUserDetails;
import bhtu.work.tthsauthservice.models.User;
import bhtu.work.tthsauthservice.models.UserAcess;
import bhtu.work.tthsauthservice.models.dto.LoginRequest;
import bhtu.work.tthsauthservice.models.dto.LoginResponse;
import bhtu.work.tthsauthservice.models.dto.SignupRequest;
import bhtu.work.tthsauthservice.models.enums.EUserAccess;
import bhtu.work.tthsauthservice.repositories.mongo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final IJwtService jwtService;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, UserRepo userRepo, PasswordEncoder passwordEncoder,
            IJwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        // get an authentication
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        // set to server
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // make JWToken corresopnding with it and include in cookies
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        if (userDetails == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        ResponseCookie jwtCookie = jwtService.generateJwtCookie(userDetails);

        // accesses maybe needed for UI display
        List<String> accesses = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                // .header(CSRF, null)
                .body(new LoginResponse(userDetails.getUsername(), accesses));
    }

    public Map<String, String> registerParent(SignupRequest signUpRequest) {
        if (this.userRepo.existsByUsername(signUpRequest.username())) {
            return Map.of("Error", "Username is already taken!");
        }

        // with parent, AccessRegion is household number, 1 family should only have 1
        // account
        if (this.userRepo.existsByAccessRegion(signUpRequest.householdNumber())) {
            return Map.of("Error", "This household already have an account!");
        }

        // Create new user's account
        User user = new User(
                null,
                signUpRequest.username(),
                passwordEncoder.encode(signUpRequest.password()),
                signUpRequest.householdNumber());

        Set<UserAcess> userAccesses = UserAcess.build(EUserAccess.READ_A_STUDENT, EUserAccess.FIX_A_STUDENT_DETAIL);

        user.setAccesses(userAccesses);
        userRepo.insert(user);

        return Map.of("Created", "true");
    }

}
