package bhtu.work.tths.accountantservice.controllers;

import bhtu.work.tths.accountantservice.models.AwardPeriod;
import bhtu.work.tths.accountantservice.proto.Verifications;
import bhtu.work.tths.accountantservice.services.AwardPeriodService;
import bhtu.work.tths.accountantservice.services.grpc.clients.Auth;
import bhtu.work.tths.share.utils.Authorizing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("api/award-period")
@CrossOrigin(originPatterns = "*", maxAge = 3600)
public class AwardPeriodController {
    private static final Logger awardPeriodControllerLogger = LoggerFactory.getLogger(AwardPeriodController.class);
    private final AwardPeriodService awardPeriodService;
    private final Auth auth;

    @Autowired
    public AwardPeriodController(AwardPeriodService awardPeriodService, Auth auth) {
        this.awardPeriodService = awardPeriodService;
        this.auth = auth;
    }

    // #region mapping
    @GetMapping("get")
    public ResponseEntity<AwardPeriod> getAwardPeriod(@RequestParam(name = "date", required = false) String dateString) {
        return ResponseEntity.ok().body(this.awardPeriodService.getAwardPeriod(dateString));
    }


    @PutMapping("update")
    public ResponseEntity<?> updateAwardLevel(
            HttpServletRequest request,
            @Valid @RequestBody AwardPeriod awardPeriod
    ) {
        final Set<Integer> VALID_ACCESS_CODES = Set.of(23, 32);

        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwt != null) {
            Verifications verifications = auth.authorize(jwt);

            // must be valid jwt
            if (verifications.getIsValid()) {
                List<String> authorities = verifications.getAuthoritiesList();
                // authority match will return non-null
                ResponseEntity<?> res = Authorizing.matchAuthority(
                        authorities,
                        VALID_ACCESS_CODES,
                        () -> {
                            this.awardPeriodService.updateAwardLevel(awardPeriod);
                            return ResponseEntity.ok().body(Map.of("isUpdated", true));
                        },
                        null,
                        awardPeriodControllerLogger::error
                );

                if (res != null) {
                    return res;
                }
            }
        }
        // if not, user not authorized
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // #endregion

}
