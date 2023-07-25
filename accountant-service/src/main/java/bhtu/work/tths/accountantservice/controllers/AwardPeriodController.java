package bhtu.work.tths.accountantservice.controllers;

import bhtu.work.tths.accountantservice.models.AwardPeriod;
import bhtu.work.tths.accountantservice.services.AwardPeriodService;
import bhtu.work.tths.accountantservice.services.grpc.clients.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/award-period")
@CrossOrigin(originPatterns = "*", maxAge = 3600)
public class AwardPeriodController {
    private final AwardPeriodService awardPeriodService;
    private final Auth auth;

    @Autowired
    public AwardPeriodController(AwardPeriodService awardPeriodService, Auth auth) {
        this.awardPeriodService = awardPeriodService;
        this.auth = auth;
    }

    // #region mapping
    @GetMapping("get")
    public ResponseEntity<AwardPeriod> getAwardPeriod(
            HttpServletRequest request,
            @RequestParam(name = "date", required = false) String dateString) {
        String jwt = Objects.requireNonNull(request.getHeaders(HttpHeaders.AUTHORIZATION)).nextElement(); // get first jwt
        var verifications = auth.authorize(jwt);
        if (!verifications.getIsValid() || !verifications.getAuthoritiesList().contains(""/*Todo: add things*/)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().body(this.awardPeriodService.getAwardPeriod(dateString));
    }

    @PutMapping("update")
    public ResponseEntity<?> updateAwardLevel(
            HttpServletRequest request,
            @Valid @RequestBody AwardPeriod awardPeriod) {
        String jwt = Objects.requireNonNull(request.getHeaders(HttpHeaders.AUTHORIZATION)).nextElement(); // get first jwt
        var verifications = auth.authorize(jwt);
        if (!verifications.getIsValid() || !verifications.getAuthoritiesList().contains(""/*Todo: add things*/)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        this.awardPeriodService.updateAwardLevel(awardPeriod);
        return ResponseEntity.ok().body(Map.of("isUpdated", true));
    }

    // #endregion

}
