package bhtu.work.tths.accountantservice.services.grpc.clients;

import bhtu.work.tths.accountantservice.proto.AuthorizeGrpc;
import bhtu.work.tths.accountantservice.proto.Jwt;
import bhtu.work.tths.accountantservice.proto.Verifications;
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
