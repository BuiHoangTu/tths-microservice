package bhtu.work.tths.statisticservice.services.grpc.clients;

import bhtu.work.tths.statisticservice.proto.AuthorizeGrpc;
import bhtu.work.tths.statisticservice.proto.Jwt;
import bhtu.work.tths.statisticservice.proto.Verifications;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class Auth {
    @GrpcClient("grpc-auth-service")
    AuthorizeGrpc.AuthorizeBlockingStub authClient;

    public Verifications authorize(String jwt) {
        return authClient.checkJwt(Jwt.newBuilder().setJwt(jwt).build());
    }
}
