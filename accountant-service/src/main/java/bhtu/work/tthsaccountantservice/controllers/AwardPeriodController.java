package bhtu.work.tthsaccountantservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bhtu.work.tthsaccountantservice.models.AwardPeriod;

import java.util.Map;
import bhtu.work.tthsaccountantservice.services.AwardPeriodService;
import javax.validation.Valid;

@RestController
@RequestMapping("api/award-period")
@CrossOrigin(originPatterns = "*", maxAge = 3600)
public class AwardPeriodController {
    private final AwardPeriodService awardPeriodService;

    @Autowired
    public AwardPeriodController(AwardPeriodService awardPeriodService) {
        this.awardPeriodService = awardPeriodService;
    }

    // #region mapping
    @GetMapping("get")
    public ResponseEntity<AwardPeriod> getAwardPeriod(
            @RequestParam(name = "date", required = false) String dateString) {
        return ResponseEntity.ok().body(this.awardPeriodService.getAwardPeriod(dateString));
    }

    @PutMapping("update")
    public ResponseEntity<?> updateAwardLevel(@Valid @RequestBody AwardPeriod awardPeriod) {
        this.awardPeriodService.updateAwardLevel(awardPeriod);
        return ResponseEntity.ok().body(Map.of("isUpdated", true));
    }

    // #endregion

}
