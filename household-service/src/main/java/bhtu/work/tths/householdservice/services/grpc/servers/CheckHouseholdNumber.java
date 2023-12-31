package bhtu.work.tths.householdservice.services.grpc.servers;

import bhtu.work.tths.householdservice.proto.VerifyGrpc;
import bhtu.work.tths.householdservice.services.HouseholdService;
import bhtu.work.tths.householdservice.proto.Existence;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class CheckHouseholdNumber extends VerifyGrpc.VerifyImplBase{
    private final HouseholdService service;

    @Autowired
    public CheckHouseholdNumber(HouseholdService service) {
        this.service = service;
    }


    @Override
    public void verifyHouseholdNumber(
            bhtu.work.tths.householdservice.proto.HouseholdNumber request,
            StreamObserver<bhtu.work.tths.householdservice.proto.Existence> responseObserver
    ) {
        var number = request.getNumber();
        var exist = service.isExist(number);

        responseObserver.onNext(Existence.newBuilder().setIsValid(exist).build());
        responseObserver.onCompleted();

    }
}
