package bhtu.work.tths.studentservice.services.grpc.servers;

import bhtu.work.tths.studentservice.models.Student;
import bhtu.work.tths.studentservice.proto.StudentHouseholdNumber;
import bhtu.work.tths.studentservice.proto.StudentId;
import bhtu.work.tths.studentservice.services.StudentService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import bhtu.work.tths.studentservice.proto.StudentServiceGrpc.StudentServiceImplBase;

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
            var eBuilder = bhtu.work.tths.studentservice.proto.Student.EventOfStudent.newBuilder();
            eBuilder.setDateOfEvent(e.getDateOfEvent().toString())
                    .setNameOfEvent(e.getNameOfEvent())
                    .setAchievement(e.getAchievement())
                    .setClassStr(e.getClassStr());

            // build prizes in event
            for (var p: e.getPrizes()) {
                var pBuilder = bhtu.work.tths.studentservice.proto.Student.EventOfStudent.PrizeGroup.newBuilder();
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

    private final StudentService studentService;


    @Autowired
    public StudentGrpcServer(StudentService studentService) {
        this.studentService = studentService;
    }


    @Override
    public void getById(StudentId request, StreamObserver<bhtu.work.tths.studentservice.proto.Student> responseObserver) {
        var mStudent = studentService.getStudentById(request.getId());

        var pStudent = mapStudent(mStudent);

        // put student to response
        responseObserver.onNext(pStudent);
        // end sending message
        responseObserver.onCompleted();
    }

    @Override
    public void getByHouseholdNumber(StudentHouseholdNumber request, StreamObserver<bhtu.work.tths.studentservice.proto.Student> responseObserver) {
        List<Student> mStudents = studentService.getStudentByHouseholdNumber(request.getHouseholdNumber());

        for (var mS: mStudents) {
            var pStudent = mapStudent(mS);
            responseObserver.onNext(pStudent);
        }
        
        responseObserver.onCompleted();
    }

}
