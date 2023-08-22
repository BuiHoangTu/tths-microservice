package bhtu.work.tths.studentservice.services.grpc.servers;

import bhtu.work.tths.studentservice.models.EventOfStudent;
import bhtu.work.tths.studentservice.models.Student;
import bhtu.work.tths.studentservice.proto.EventDate;
import bhtu.work.tths.studentservice.proto.EventName;
import bhtu.work.tths.studentservice.proto.StudentId;
import bhtu.work.tths.studentservice.repositories.mongo.StudentRepo;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import bhtu.work.tths.studentservice.proto.StudentServiceGrpc.StudentServiceImplBase;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class StudentGrpcServer extends StudentServiceImplBase {
    /**
     * Map model student to proto student
     * @param mStudent model student
     * @return proto student
     */
    private static bhtu.work.tths.studentservice.proto.Student mapStudent(Student mStudent) {
        var pStudentBuilder = bhtu.work.tths.studentservice.proto.Student.newBuilder();

        pStudentBuilder.setId(mStudent.getId())
                .setName(mStudent.getName())
                .setParent(mStudent.getParent())
                .setDateOfBirth(mStudent.getDateOfBirth().toString())
                .setHouseholdNumber(mStudent.getHouseholdNumber())
                .setParent(mStudent.getParent());

        for (var e: mStudent.getEvents()) {
            // build events
            var eBuilder = bhtu.work.tths.studentservice.proto.EventOfStudent.newBuilder();
            eBuilder.setDateOfEvent(e.getDateOfEvent().toString())
                    .setNameOfEvent(e.getNameOfEvent())
                    .setAchievement(e.getAchievement())
                    .setClassStr(e.getClassStr());

            // build prizes in event
            for (var p: e.getPrizes()) {
                var pBuilder = bhtu.work.tths.studentservice.proto.EventOfStudent.PrizeGroup.newBuilder();
                pBuilder.setNameOfPrize(p.getNameOfPrize())
                        .setAmount(p.getAmount());
                // add prize to event
                eBuilder.addPrizeGroup(pBuilder);
            }
            // add event to student
            pStudentBuilder.addEventOfStudent(eBuilder);
        }

        return pStudentBuilder.build();
    }

    private static bhtu.work.tths.studentservice.proto.EventOfStudent mapEvent(EventOfStudent mEvent) {
        var pSBuilder = bhtu.work.tths.studentservice.proto.EventOfStudent.newBuilder();

        pSBuilder
                .setNameOfEvent(mEvent.getNameOfEvent())
                .setDateOfEvent(mEvent.getDateOfEvent().toString())
                .setAchievement(mEvent.getAchievement())
                .setClassStr(mEvent.getClassStr())
                .setTotalExpense(mEvent.getTotalExpense());

        var pgBuilder = bhtu.work.tths.studentservice.proto.EventOfStudent.PrizeGroup.newBuilder();

        for(var pg: mEvent.getPrizes()) {
            var ppg = pgBuilder
                    .setAmount(pg.getAmount())
                    .setNameOfPrize(pg.getNameOfPrize())
                    .build();
            pSBuilder.addPrizeGroup(ppg);
        }
        return pSBuilder.build();
    }

    private final StudentRepo studentRepo;


    @Autowired
    public StudentGrpcServer(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }


    @Override
    public void getById(StudentId request, StreamObserver<bhtu.work.tths.studentservice.proto.Student> responseObserver) {
        var oStudent = studentRepo.findById(request.getIdentifier());

        if (oStudent.isEmpty()) {
            responseObserver.onError(new Exception("This student id is not existed"));
            responseObserver.onCompleted();
        } else {
            var mStudent = oStudent.get();
            var pStudent = mapStudent(mStudent);
            // put student to response
            responseObserver.onNext(pStudent);
            // end sending message
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getByHouseholdNumber(StudentId request, StreamObserver<bhtu.work.tths.studentservice.proto.Student> responseObserver) {
        List<Student> mStudents = studentRepo.findByHouseholdNumber(request.getIdentifier());

        for (var mS: mStudents) {
            var pStudent = mapStudent(mS);
            responseObserver.onNext(pStudent);
        }
        
        responseObserver.onCompleted();
    }

    @Override
    public void getByEventDate(EventDate request, StreamObserver<bhtu.work.tths.studentservice.proto.EventOfStudent> responseObserver) {
        try {
            List<EventOfStudent> prizes = studentRepo.findPrizeGroupByEvents_DateOfEvent(LocalDate.parse(request.getDate()));

            for (var p: prizes) {
                var pe = mapEvent(p);
                responseObserver.onNext(pe);
            }

            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getByEventName(EventName request, StreamObserver<bhtu.work.tths.studentservice.proto.EventOfStudent> responseObserver) {
        List<EventOfStudent> prizes = studentRepo.findPrizeGroupByEvents_NameOfEvent(request.getName());

        for (var p: prizes) {
            var pe = mapEvent(p);
            responseObserver.onNext(pe);
        }

        responseObserver.onCompleted();
    }
}
