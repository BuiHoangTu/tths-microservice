package bhtu.work.tths.statisticservice.services;

import bhtu.work.tths.share.utils.counter.StackCounter;
import bhtu.work.tths.statisticservice.models.EventOfStudent;
import bhtu.work.tths.statisticservice.models.PrizeGroup;
import bhtu.work.tths.statisticservice.models.dto.RewardByEvent;
import bhtu.work.tths.statisticservice.models.dto.RewardByHouseholdNumber;
import bhtu.work.tths.statisticservice.services.grpc.clients.RewardDetailGrpcClient;
import bhtu.work.tths.statisticservice.services.grpc.clients.StudentGrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class StatisticService {
    private static final Logger STATISTIC_SERVICE_LOGGER = LoggerFactory.getLogger(StatisticService.class);

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
        var refinedFilterType = filterType.toLowerCase();
        switch (refinedFilterType) {
            case "date" -> {
                return this.eventDate(eventFilter);
            }

            case "name" -> {
                return this.eventName(eventFilter);
            }

            default -> throw new IllegalArgumentException("The option [`" + filterType + "`]is not available");
        }

    }

    public RewardByHouseholdNumber getByHouseholdNumber(String householdNumber) {
        var students = studentGrpc.getStudentByHouseholdNumber(householdNumber);
        StackCounter<PrizeGroup> counter = new StackCounter<>();

        while (students.hasNext()) {
            var student = students.next();
            for (var event : student.getEvents()) {
                STATISTIC_SERVICE_LOGGER.info("Putting {} inside counter", event);
                counter.addAll(event.getPrizes());
            }
        }
        STATISTIC_SERVICE_LOGGER.info("Finish counting");
        return new RewardByHouseholdNumber(counter.stream().toList(), householdNumber);
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
        StackCounter<EventOfStudent> counter = new StackCounter<>();

        while (iterator.hasNext()) {
            var eventOfStudent = iterator.next();
            counter.count(eventOfStudent);
        }

        var out = new ArrayList<RewardByEvent>();

        counter.forEach((event) -> {
            out.add(new RewardByEvent(event.getPrizes().stream().toList(), event.getTotalExpense(), event.getDateOfEvent(), event.getNameOfEvent()));
        });

        return out;
    }
}
