package bhtu.work.tths.statisticservice.services;

import bhtu.work.tths.statisticservice.models.PrizeGroup;
import bhtu.work.tths.statisticservice.models.dto.RewardByEvent;
import bhtu.work.tths.statisticservice.models.dto.RewardByHouseholdNumber;
import bhtu.work.tths.statisticservice.services.grpc.clients.RewardDetailGrpcClient;
import bhtu.work.tths.statisticservice.services.grpc.clients.StudentGrpcClient;
import bhtu.work.tths.statisticservice.utils.PrizeCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

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
     */
    public RewardByEvent getRewardByEvent(String eventFilter, String filterType) throws IllegalArgumentException {

        return null;
        // TODO: implement this and below, match whole name - all in that date
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
}
