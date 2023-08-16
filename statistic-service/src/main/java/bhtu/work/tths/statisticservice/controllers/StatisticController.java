package bhtu.work.tths.statisticservice.controllers;

import bhtu.work.tths.share.utils.Authorizing;
import bhtu.work.tths.statisticservice.models.dto.RewardByEvent;
import bhtu.work.tths.statisticservice.models.dto.RewardByHouseholdNumber;
import bhtu.work.tths.statisticservice.services.grpc.clients.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bhtu.work.tths.statisticservice.services.StatisticService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/statistic")
public class StatisticController {
    private static final Logger statisticControllerLogger = LoggerFactory.getLogger(StatisticController.class);
    private final StatisticService statisticService;
    private final Auth auth;

    @Autowired
    public StatisticController(StatisticService statisticService, Auth auth) {
        this.statisticService = statisticService;
        this.auth = auth;
    }

    // #region mapping

    /**
     * User want to know how much Reward is given in an event
     */
    @GetMapping("event")
    public ResponseEntity<?> getRewardByEvent(
            HttpServletRequest request,
            @RequestParam(name = "filter") String eventFilter,
            @RequestParam(defaultValue = "eventname") String filterType
    ) {
        final Set<Integer> VALID_ACCESS_CODES = Set.of(23, 32);

        String jwt = null;
        try {
            jwt = Objects.requireNonNull(request.getHeaders(HttpHeaders.AUTHORIZATION)).nextElement(); // get first jwt
        } catch (NullPointerException ignored) {
        }

        var verifications = auth.authorize(jwt);

        if (verifications.getIsValid()) {
            List<String> authorities = verifications.getAuthoritiesList();

            var res = Authorizing.matchAuthority(
                    authorities,
                    VALID_ACCESS_CODES,
                    () -> {
                        try {
                            return ResponseEntity.ok().body(this.statisticService.getRewardByEvent(eventFilter, filterType));
                        } catch (IllegalArgumentException e) {
                            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
                        }
                    },
                    null,
                    statisticControllerLogger::error
            );
            if (res != null) return res;
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("household-number")
    public ResponseEntity<?> getByHouseholdNumber(
            HttpServletRequest request,
            @RequestParam(name = "filter") String householdNumber
    ) {
        final Set<Integer> VALID_ACCESS_CODES = Set.of(23,32);

        String jwt = null;
        try {
            jwt = Objects.requireNonNull(request.getHeaders(HttpHeaders.AUTHORIZATION)).nextElement(); // get first jwt
        } catch (NullPointerException ignored) {}

        var verifications = auth.authorize(jwt);

        if (verifications.getIsValid()) {
            List<String> authorities = verifications.getAuthoritiesList();
            // authority match will return non-null
            var res = Authorizing.matchAuthority(
                    authorities,
                    VALID_ACCESS_CODES,
                    () -> {
                        try {
                            return ResponseEntity.ok().body(this.statisticService.getByHouseholdNumber(householdNumber));
                        } catch (Exception e) {
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                        }
                    },
                    null,
                    statisticControllerLogger::error
            );
            if (res != null) return res;
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();


    }
    // #endregion
}
