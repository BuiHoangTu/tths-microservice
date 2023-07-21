package bhtu.work.tths.statisticservice.services.grpc.clients;

import bhtu.work.tths.statisticservice.models.EventOfStudent;
import bhtu.work.tths.statisticservice.models.PrizeGroup;
import bhtu.work.tths.statisticservice.models.Student;
import com.google.common.util.concurrent.Futures;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.Future;

@Service
public class StudentGrpcClient {
    /**
     * Map proto student to model student
     * @param pStudent proto student
     * @return models.Student
     */
    private static Student mapStudent(bhtu.work.tths.statisticservice.proto.Student pStudent) {
        Student mStudent = new Student(
                pStudent.getId(),
                pStudent.getName(),
                LocalDate.parse(pStudent.getDateOfBirth()),
                null,
                pStudent.getHouseholdNumber(),
                pStudent.getParent());

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
    bhtu.work.tths.statisticservice.proto.StudentServiceGrpc.StudentServiceFutureStub studentClient;

    public Future<Student> getStudentById(String id) {
        var request = bhtu.work.tths.statisticservice.proto.StudentId.newBuilder().setId(id).build();
        return Futures.lazyTransform(studentClient.getById(request), StudentGrpcClient::mapStudent);
    }

    public Future<Student> getStudentByHouseholdNumber(
            String householdNumber) {
        var request = bhtu.work.tths.statisticservice.proto.StudentHouseholdNumber.newBuilder()
                .setHouseholdNumber(householdNumber).build();

        return Futures.lazyTransform(studentClient.getByHouseholdNumber(request), StudentGrpcClient::mapStudent);
    }
}
