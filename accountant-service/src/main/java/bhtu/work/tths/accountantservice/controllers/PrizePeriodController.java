package bhtu.work.tths.accountantservice.controllers;

import bhtu.work.tths.accountantservice.services.PrizePeriodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bhtu.work.tths.accountantservice.models.PrizePeriod;

import java.util.Map;

@RestController
@RequestMapping("api/prize-period")
public class PrizePeriodController {
    private final PrizePeriodService prizePeriodService;

    public PrizePeriodController(PrizePeriodService prizePeriodService) {
        this.prizePeriodService = prizePeriodService;
    }

    // #region mapping
    @GetMapping("get")
    public ResponseEntity<PrizePeriod> getPrizePeriod(
            @RequestParam(name = "date", required = false) String dateString) {
        return ResponseEntity.ok().body(this.prizePeriodService.getAwardPeriod(dateString));
    }

    @PutMapping("update")
    public ResponseEntity<?> updatePrizeLevel(@RequestBody PrizePeriod prizePeriod) {
        this.prizePeriodService.updateAwardLevel(prizePeriod);
        return ResponseEntity.ok().body(Map.of("isUpdated", true));
    }
    // #endregion
}