package bhtu.work.tths.statisticservice.services.grpc.clients;

import bhtu.work.tths.share.utils.BlockingIterator;
import bhtu.work.tths.statisticservice.models.EventOfStudent;
import bhtu.work.tths.statisticservice.models.PrizeGroup;
import bhtu.work.tths.statisticservice.models.Student;
import bhtu.work.tths.statisticservice.proto.EventDate;
import bhtu.work.tths.statisticservice.proto.EventName;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Iterator;

@Service
public class StudentGrpcClient {
    private static final Logger STUDENT_GRPC_LOG = LoggerFactory.getLogger(StudentGrpcClient.class);

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
        mEvent.setPrizes(pEvent.getPrizeGroupList().stream().map((pPrize) -> new PrizeGroup(pPrize.getNameOfPrize(), pPrize.getAmount())).toList());

        return mEvent;
    }


    @net.devh.boot.grpc.client.inject.GrpcClient("grpc-student-service")
    private bhtu.work.tths.statisticservice.proto.StudentServiceGrpc.StudentServiceStub studentClient;


    public Iterator<Student> getStudentByHouseholdNumber(String householdNumber) {
        var request = bhtu.work.tths.statisticservice.proto.StudentId.newBuilder()
                .setIdentifier(householdNumber)
                .build();

        BlockingIterator<Student> students = new BlockingIterator<>();
        studentClient.getByHouseholdNumber(request, new StreamObserver<>() {
            @Override
            public void onNext(bhtu.work.tths.statisticservice.proto.Student student) {
                students.offer(mapStudent(student));
            }

            @Override
            public void onError(Throwable throwable) {
                STUDENT_GRPC_LOG.error(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                students.close();
            }


        });

        return students;
    }

    public Iterator<EventOfStudent> getByEventDate(LocalDate date) {
        BlockingIterator<EventOfStudent> out = new BlockingIterator<>();
        studentClient.getByEventDate(EventDate.newBuilder().setDate(date.toString()).build(), new EventOfStudentStream(out));
        return out;
    }

    public Iterator<EventOfStudent> getByEventName(String name) {
        BlockingIterator<EventOfStudent> out = new BlockingIterator<>();
        studentClient.getByEventName(EventName.newBuilder().setName(name).build(), new EventOfStudentStream(out));
        return out;
    }

    private record EventOfStudentStream(
            BlockingIterator<EventOfStudent> messageQueue
    ) implements StreamObserver<bhtu.work.tths.statisticservice.proto.EventOfStudent> {

        @Override
        public void onNext(bhtu.work.tths.statisticservice.proto.EventOfStudent eventOfStudent) {
            messageQueue.offer(mapEvent(eventOfStudent));
        }

        @Override
        public void onError(Throwable throwable) {
            STUDENT_GRPC_LOG.error(throwable.getMessage());
        }

        @Override
        public void onCompleted() {
            messageQueue.close();
        }
    }
}
