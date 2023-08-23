package bhtu.work.tths.statisticservice.services;

import bhtu.work.tths.statisticservice.models.EventOfStudent;
import bhtu.work.tths.statisticservice.models.dto.RewardByEvent;
import bhtu.work.tths.statisticservice.models.dto.RewardByHouseholdNumber;
import bhtu.work.tths.statisticservice.services.grpc.clients.RewardDetailGrpcClient;
import bhtu.work.tths.statisticservice.services.grpc.clients.StudentGrpcClient;
import bhtu.work.tths.statisticservice.utils.EventCounter;
import bhtu.work.tths.statisticservice.utils.PrizeCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class StatisticService {
    private final StudentGrpcClient studentGrpc;
    private final RewardDetailGrpcClient rewardGrpc;


    @Autowired
    public StatisticService(StudentGrpcClient studentGrpc, RewardDetailGrpcClient rewardGrpc) {
        this.studentGrpc = studentGrpc;
        this.rewardGrpc = rewardGrpc;
    }

    /**
     * Reward of all events that match whole
     * @param filterType type of filter: date or name
     * @param eventFilter date or name respectively, must match whole
     * @return all Reward from the matched events
     * @throws IllegalArgumentException if filterType does not match
     * @throws java.time.format.DateTimeParseException if filterType == "date" and eventFilter can't be parsed to LocalDate
     */
    public List<RewardByEvent> getRewardByEvent(String eventFilter, String filterType) throws IllegalArgumentException {
        filterType = filterType.toLowerCase();
        switch (filterType) {
            case "date" -> {
                return this.eventDate(eventFilter);
            }

            case "name" -> {
                return this.eventName(eventFilter);
            }

            default -> throw new IllegalArgumentException("The option " + filterType + "is not available");
        }

    }

    public RewardByHouseholdNumber getByHouseholdNumber(String householdNumber) {
        var students = studentGrpc.getStudentByHouseholdNumber(householdNumber);
        PrizeCounter counter = new PrizeCounter();

        while (students.hasNext()) {
            var student = students.next();
            for (var event : student.getEvents()) {
                counter.putAll(event.getPrizes());
            }
        }
        return new RewardByHouseholdNumber(counter.values().stream().toList(), householdNumber);
    }


    /**
     * @throws java.time.format.DateTimeParseException if date is not parsable
     */
    private List<RewardByEvent> eventDate(String strDate) {
        LocalDate date = LocalDate.parse(strDate);
        var iterator = studentGrpc.getByEventDate(date);
        return squashEvent(iterator);
    }
    private List<RewardByEvent> eventName(String name) {
        var iterator = studentGrpc.getByEventName(name);
        return squashEvent(iterator);
    }

    private List<RewardByEvent> squashEvent(Iterator<EventOfStudent> iterator) {
        EventCounter counter = new EventCounter();

        while (iterator.hasNext()) {
            var eventOfStudent = iterator.next();
            counter.put(eventOfStudent);
        }

        var out = new ArrayList<RewardByEvent>();

        counter.entrySet().forEach((entry) -> {
            var c = entry.getValue();
            var ev = entry.getKey();
            //Todo: add rewards
            out.add(new RewardByEvent(null, c.intValue(), ev.getDateOfEvent(), ev.getNameOfEvent()));
        });

        return out;
    }
}
