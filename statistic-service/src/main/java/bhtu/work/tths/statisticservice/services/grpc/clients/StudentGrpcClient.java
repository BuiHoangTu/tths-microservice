package bhtu.work.tths.statisticservice.services.grpc.clients;

import bhtu.work.tths.statisticservice.models.EventOfStudent;
import bhtu.work.tths.statisticservice.models.PrizeGroup;
import bhtu.work.tths.statisticservice.models.Student;
import bhtu.work.tths.share.utils.BlockingIterator;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.stream.Stream;

@Service
public class StudentGrpcClient {
    private static final Logger studentGrpcLog = LoggerFactory.getLogger(StudentGrpcClient.class);

    /**
     * Map proto student to model student
     *
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
    bhtu.work.tths.statisticservice.proto.StudentServiceGrpc.StudentServiceStub studentClient;


    public Iterator<Student> getStudentByHouseholdNumber(
            String householdNumber) {
        var request = bhtu.work.tths.statisticservice.proto.StudentId.newBuilder()
                .setIdentifier(householdNumber).build();

        BlockingIterator<Student> students = new BlockingIterator<>();
        studentClient.getByHouseholdNumber(request, new StreamObserver<>() {
            @Override
            public void onNext(bhtu.work.tths.statisticservice.proto.Student student) {
                students.offer(mapStudent(student));
            }

            @Override
            public void onError(Throwable throwable) {
                studentGrpcLog.error(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                students.close();
            }


        });

        return students;
    }

    public Stream<Student> getByEvent(String date, String name) {
        return null;
    }
}
