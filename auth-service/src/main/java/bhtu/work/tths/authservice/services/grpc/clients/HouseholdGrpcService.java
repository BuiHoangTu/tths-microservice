package bhtu.work.tths.authservice.services.grpc.clients;

import bhtu.work.tths.authservice.proto.HouseholdNumber;
import bhtu.work.tths.authservice.proto.VerifyGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class HouseholdGrpcService {
    @GrpcClient("grpc-household")
    VerifyGrpc.VerifyBlockingStub client;

    public boolean verify(String householdNumber) {
        var response = client.verifyHouseholdNumber(HouseholdNumber.newBuilder().setNumber(householdNumber).build());
        return response.getIsValid();
    }
}
