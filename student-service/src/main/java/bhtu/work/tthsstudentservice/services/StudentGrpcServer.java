package bhtu.work.tthsstudentservice.services;

import bhtu.work.tthsstudentservice.proto.Student;
import bhtu.work.tthsstudentservice.proto.StudentHouseholdNumber;
import bhtu.work.tthsstudentservice.proto.StudentId;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import bhtu.work.tthsstudentservice.proto.StudentServiceGrpc.StudentServiceImplBase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class StudentGrpcServer extends StudentServiceImplBase {
    /**
     * Map model student to proto student
     * @param mStudent model student
     * @return proto student
     */
    private static Student mapStudent(bhtu.work.tthsstudentservice.models.Student mStudent) {
        Student.Builder pStudentBuilder = Student.newBuilder();

        pStudentBuilder.setId(mStudent.getId())
                .setName(mStudent.getName())
                .setParent(mStudent.getParent())
                .setDateOfBirth(mStudent.getDateOfBirth().toString())
                .setHouseholdNumber(mStudent.getHouseholdNumber())
                .setParent(mStudent.getParent());

        for (var e: mStudent.getEvents()) {
            // build events
            var eBuilder = Student.EventOfStudent.newBuilder();
            eBuilder.setDateOfEvent(e.getDateOfEvent().toString())
                    .setNameOfEvent(e.getNameOfEvent())
                    .setAchievement(e.getAchievement())
                    .setClassStr(e.getClassStr());

            // build prizes in event
            for (var p: e.getPrizes()) {
                var pBuilder = Student.EventOfStudent.PrizeGroup.newBuilder();
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
    public void getById(StudentId request, StreamObserver<Student> responseObserver) {
        var mStudent = studentService.getStudentById(request.getId());

        var pStudent = mapStudent(mStudent);

        // put student to response
        responseObserver.onNext(pStudent);
        // end sending message
        responseObserver.onCompleted();
    }

    @Override
    public void getByHouseholdNumber(StudentHouseholdNumber request, StreamObserver<Student> responseObserver) {
        List<bhtu.work.tthsstudentservice.models.Student> mStudents = studentService.getStudentByHouseholdNumber(request.getHouseholdNumber());

        for (var mS: mStudents) {
            var pStudent = mapStudent(mS);
            responseObserver.onNext(pStudent);
        }
        
        responseObserver.onCompleted();
    }

}
