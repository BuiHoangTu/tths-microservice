package bhtu.work.tths.accountantservice.controllers;

import bhtu.work.CheckAccesses;
import bhtu.work.tths.accountantservice.models.AwardPeriod;
import bhtu.work.tths.accountantservice.services.AwardPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
            HttpServletRequest request,
            @RequestParam(name = "date", required = false) String dateString) {
        if (CheckAccesses.checkHeaders(request, "" /*Todo: add*/)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().body(this.awardPeriodService.getAwardPeriod(dateString));
    }

    @PutMapping("update")
    public ResponseEntity<?> updateAwardLevel(
            HttpServletRequest request,
            @Valid @RequestBody AwardPeriod awardPeriod) {
        if (CheckAccesses.checkHeaders(request, "" /*Todo: add*/)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("isUpdated", false));
        }

        this.awardPeriodService.updateAwardLevel(awardPeriod);
        return ResponseEntity.ok().body(Map.of("isUpdated", true));
    }

    // #endregion

}
