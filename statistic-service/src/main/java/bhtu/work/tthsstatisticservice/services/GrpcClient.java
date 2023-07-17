package bhtu.work.tthsstatisticservice.services;

import bhtu.work.tthsstatisticservice.models.EventOfStudent;
import bhtu.work.tthsstatisticservice.models.PrizeGroup;
import bhtu.work.tthsstatisticservice.models.Student;
import com.google.common.util.concurrent.ListenableFuture;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GrpcClient {
    public static Student mapStudent(bhtu.work.tthsstatisticservice.proto.Student pStudent) {
        Student mStudent = new Student(
                pStudent.getId(),
                pStudent.getName(),
                LocalDate.parse(pStudent.getDateOfBirth()),
                null,
                pStudent.getHouseholdNumber(),
                pStudent.getParent()
        );

        mStudent.setEvents(pStudent.getEventOfStudentList()
                .stream()
                .map((pEvent) -> {
                    var mEvent = new EventOfStudent();
                    mEvent.setAchievement(pEvent.getAchievement());
                    mEvent.setDateOfEvent(LocalDate.parse(pEvent.getDateOfEvent()));
                    mEvent.setClassStr(pEvent.getClassStr());

                    mEvent.setPrizes(pEvent.getPrizeGroupList()
                            .stream()
                            .map((pPrize) -> new PrizeGroup(pPrize.getNameOfPrize(), pPrize.getAmount()))
                            .toList());

                    return mEvent;
                })
                .toList());

        return mStudent;
    }

    @net.devh.boot.grpc.client.inject.GrpcClient("grpc-student-service")
    bhtu.work.tthsstatisticservice.proto.StudentServiceGrpc.StudentServiceFutureStub asyncClient;

    public ListenableFuture<bhtu.work.tthsstatisticservice.proto.Student> getStudentById(String id) {
        var request = bhtu.work.tthsstatisticservice.proto.StudentId.newBuilder().setId(id).build();
        return asyncClient.getById(request);
    }

}
