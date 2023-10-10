package bhtu.work.tths.core.models.dto;

import java.time.LocalDate;

import bhtu.work.tths.core.models.EventOfStudent;

public record StudentOneReward(
        String id,
        String name,
        LocalDate dateOfBirth,
        String school,
        String householdNumber,
        String parent,
        EventOfStudent lastestEvent) {

}
