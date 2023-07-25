package bhtu.work.tths.accountantservice.controllers;

import bhtu.work.tths.accountantservice.services.PrizePeriodService;
import bhtu.work.tths.accountantservice.services.grpc.clients.Auth;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bhtu.work.tths.accountantservice.models.PrizePeriod;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/prize-period")
public class PrizePeriodController {
    private final PrizePeriodService prizePeriodService;
    private final Auth auth;

    public PrizePeriodController(PrizePeriodService prizePeriodService, Auth auth) {
        this.prizePeriodService = prizePeriodService;
        this.auth = auth;
    }

    // #region mapping
    @GetMapping("get")
    public ResponseEntity<PrizePeriod> getPrizePeriod(
            HttpServletRequest request,
            @RequestParam(name = "date", required = false) String dateString) {
        String jwt = Objects.requireNonNull(request.getHeaders(HttpHeaders.AUTHORIZATION)).nextElement(); // get first jwt
        var verifications = auth.authorize(jwt);
        if (!verifications.getIsValid() || !verifications.getAuthoritiesList().contains(""/*Todo: add things*/)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().body(this.prizePeriodService.getAwardPeriod(dateString));
    }

    @PutMapping("update")
    public ResponseEntity<?> updatePrizeLevel(
            HttpServletRequest request,
            @RequestBody PrizePeriod prizePeriod) {
        String jwt = Objects.requireNonNull(request.getHeaders(HttpHeaders.AUTHORIZATION)).nextElement(); // get first jwt
        var verifications = auth.authorize(jwt);
        if (!verifications.getIsValid() || !verifications.getAuthoritiesList().contains(""/*Todo: add things*/)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        this.prizePeriodService.updateAwardLevel(prizePeriod);
        return ResponseEntity.ok().body(Map.of("isUpdated", true));
    }
    // #endregion
}
