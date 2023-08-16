package bhtu.work.tths.accountantservice.controllers;

import bhtu.work.tths.accountantservice.models.PrizePeriod;
import bhtu.work.tths.accountantservice.services.PrizePeriodService;
import bhtu.work.tths.accountantservice.services.grpc.clients.Auth;
import bhtu.work.tths.share.models.enums.EUserAccess;
import bhtu.work.tths.share.utils.Authorizing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("api/prize-period")
public class PrizePeriodController {
    private static final Logger prizePeriodControllerLogger = LoggerFactory.getLogger(PrizePeriodController.class);
    private final PrizePeriodService prizePeriodService;
    private final Auth auth;

    public PrizePeriodController(PrizePeriodService prizePeriodService, Auth auth) {
        this.prizePeriodService = prizePeriodService;
        this.auth = auth;
    }

    // #region mapping
    @GetMapping("get")
    public ResponseEntity<PrizePeriod> getPrizePeriod(@RequestParam(name = "date", required = false) String dateString) {
        return ResponseEntity.ok().body(this.prizePeriodService.getAwardPeriod(dateString));
    }

    @PutMapping("update")
    public ResponseEntity<?> updatePrizeLevel(
            HttpServletRequest request,
            @RequestBody PrizePeriod prizePeriod) {
        final var VALID_ACCESS_CODE = Set.of(23, 32);

        String jwt = null;
        try {
            Objects.requireNonNull(request.getHeaders(HttpHeaders.AUTHORIZATION)).nextElement(); // get first jwt
        } catch (NullPointerException ignored) {}

        var verifications = auth.authorize(jwt);

        // must be valid
        if (verifications.getIsValid()) {
            List<String> authorities = verifications.getAuthoritiesList();

            // and one of authorities must match
            var res = Authorizing.matchAuthority(
                    authorities,
                    VALID_ACCESS_CODE,
                    () -> {
                        this.prizePeriodService.updateAwardLevel(prizePeriod);
                        return ResponseEntity.ok().body(Map.of("isUpdated", true));
                    },
                    null,
                    prizePeriodControllerLogger::error
            );
            if (res != null) return res;
        }
        // if not, user not authorized
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    // #endregion
}
