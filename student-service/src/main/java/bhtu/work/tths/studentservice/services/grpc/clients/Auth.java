package bhtu.work.tths.studentservice.services.grpc.clients;

import bhtu.work.tths.studentservice.proto.AuthorizeGrpc;
import bhtu.work.tths.studentservice.proto.Jwt;
import bhtu.work.tths.studentservice.proto.Verifications;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class Auth {
    @GrpcClient("auth-service")
    AuthorizeGrpc.AuthorizeBlockingStub authClient;

    public Verifications authorize(String jwt) {
        return authClient.checkJwt(Jwt.newBuilder().setJwt(jwt).build());
    }
}
