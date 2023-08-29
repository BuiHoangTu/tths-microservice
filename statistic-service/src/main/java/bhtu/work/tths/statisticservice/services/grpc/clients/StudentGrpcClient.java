package bhtu.work.tths.statisticservice.services.grpc.clients;

import bhtu.work.tths.share.utils.BlockingIterator;
import bhtu.work.tths.statisticservice.models.EventOfStudent;
import bhtu.work.tths.statisticservice.models.PrizeGroup;
import bhtu.work.tths.statisticservice.models.Student;
import bhtu.work.tths.statisticservice.proto.EventDate;
import bhtu.work.tths.statisticservice.proto.EventName;
import bhtu.work.tths.statisticservice.proto.StudentServiceGrpc;
import com.google.common.collect.Iterators;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.stream.Collectors;

@Service
public class StudentGrpcClient {

    /**
     * Map proto student to model student
     *
     * @param pStudent proto student
     * @return models.Student
     */
    private static Student mapStudent(bhtu.work.tths.statisticservice.proto.Student pStudent) {
        Student mStudent = new Student(
                null,
                null,
                null,
                null,
                null,
                pStudent.getParent());

        mStudent.setEvents(pStudent.getEventOfStudentList()
                .stream()
                .map(StudentGrpcClient::mapEvent)
                .toList());

        return mStudent;
    }

    private static EventOfStudent mapEvent(bhtu.work.tths.statisticservice.proto.EventOfStudent pEvent) {
        var mEvent = new EventOfStudent();
        mEvent.setAchievement(pEvent.getAchievement());
        mEvent.setNameOfEvent(pEvent.getNameOfEvent());
        mEvent.setDateOfEvent(LocalDate.parse(pEvent.getDateOfEvent()));
        mEvent.setClassStr(pEvent.getClassStr());
        mEvent.setTotalExpense(pEvent.getTotalExpense());
        mEvent.setPrizes(pEvent.getPrizeGroupList().stream().map((pPrize) -> new PrizeGroup(pPrize.getNameOfPrize(), pPrize.getAmount())).collect(Collectors.toSet()));

        return mEvent;
    }


    @net.devh.boot.grpc.client.inject.GrpcClient("grpc-student-service")
    private StudentServiceGrpc.StudentServiceBlockingStub studentClient;


    public Iterator<Student> getStudentByHouseholdNumber(String householdNumber) {
        var request = bhtu.work.tths.statisticservice.proto.StudentId.newBuilder()
                .setIdentifier(householdNumber)
                .build();

        return Iterators.transform(studentClient.getByHouseholdNumber(request), StudentGrpcClient::mapStudent);

    }

    public Iterator<EventOfStudent> getByEventDate(LocalDate date) {
        return Iterators.transform(studentClient.getByEventDate(EventDate.newBuilder().setDate(date.toString()).build()), StudentGrpcClient::mapEvent);
    }

    public Iterator<EventOfStudent> getByEventName(String name) {
        return Iterators.transform(studentClient.getByEventName(EventName.newBuilder().setName(name).build()), StudentGrpcClient::mapEvent);
    }

}
