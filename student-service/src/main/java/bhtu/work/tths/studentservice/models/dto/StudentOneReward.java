package bhtu.work.tths.studentservice.models.dto;

import java.time.LocalDate;

import bhtu.work.tths.studentservice.models.EventOfStudent;

public record StudentOneReward(
        String id,
        String name,
        LocalDate dateOfBirth,
        String school,
        String householdNumber,
        String parent,
        EventOfStudent lastestEvent) {

}
