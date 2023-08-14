package bhtu.work.tths.statisticservice.controllers;

import bhtu.work.tths.statisticservice.models.dto.RewardByEvent;
import bhtu.work.tths.statisticservice.models.dto.RewardByHouseholdNumber;
import bhtu.work.tths.statisticservice.services.grpc.clients.Auth;
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
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/statistic")
public class StatisticController {
    private final StatisticService statisticService;
    private final Auth auth;

    @Autowired
    public StatisticController(StatisticService statisticService, Auth auth) {
        this.statisticService = statisticService;
        this.auth = auth;
    }

    // #region mapping
    @GetMapping("event")
    public ResponseEntity<RewardByEvent> getRewardByEvent(
            HttpServletRequest request,
            @RequestParam(name = "filter") String eventFilter,
            @RequestParam(defaultValue = "eventname") String filterType) {
        String jwt = Objects.requireNonNull(request.getHeaders(HttpHeaders.AUTHORIZATION)).nextElement(); // get first jwt
        var verifications = auth.authorize(jwt);
        if (!verifications.getIsValid() || !verifications.getAuthoritiesList().contains(""/*Todo: add things*/)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().body(this.statisticService.getRewardByEvent(eventFilter, filterType));
    }

    @GetMapping("household-number")
    public ResponseEntity<RewardByHouseholdNumber> getByHouseholdNumber(
            HttpServletRequest request,
            @RequestParam(name = "filter") String householdNumber
    ) {
        String jwt = Objects.requireNonNull(request.getHeaders(HttpHeaders.AUTHORIZATION)).nextElement(); // get first jwt
        var verifications = auth.authorize(jwt);
        if (!verifications.getIsValid() || !verifications.getAuthoritiesList().contains(""/*Todo: add things*/)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            return ResponseEntity.ok().body(this.statisticService.getByHouseholdNumber(householdNumber));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // #endregion
}
