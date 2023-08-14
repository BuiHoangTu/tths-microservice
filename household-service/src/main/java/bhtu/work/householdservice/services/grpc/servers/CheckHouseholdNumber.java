package bhtu.work.householdservice.services.grpc.servers;

import bhtu.work.householdservice.services.HouseholdService;
import bhtu.work.tths.householdservice.proto.AuthorizeGrpc;
import bhtu.work.tths.householdservice.proto.Verifications;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class CheckHouseholdNumber extends AuthorizeGrpc.AuthorizeImplBase{
    private final HouseholdService service;

    @Autowired
    public CheckHouseholdNumber(HouseholdService service) {
        this.service = service;
    }


    @Override
    public void verifyHouseholdNumber(
            bhtu.work.tths.householdservice.proto.HouseholdNumber request,
            StreamObserver<bhtu.work.tths.householdservice.proto.Verifications> responseObserver
    ) {
        var number = request.getNumber();
        var exist = service.isExist(number);

        responseObserver.onNext(Verifications.newBuilder().setIsValid(exist).build());
        responseObserver.onCompleted();

    }
}
