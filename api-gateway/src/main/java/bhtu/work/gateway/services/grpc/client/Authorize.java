package bhtu.work.gateway.services.grpc.client;

import bhtu.work.gateway.models.Auth;
import bhtu.work.tths.gateway.proto.AuthorizeGrpc;
import bhtu.work.tths.gateway.proto.Jwt;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;

@Service
public class Authorize {
    @GrpcClient("grpc-authorize")
    AuthorizeGrpc.AuthorizeBlockingStub authClient;

    public Auth getAuthorities(String jwt) {
        var req = Jwt.newBuilder().setJwt(jwt);

        var res = authClient.checkJwt(req.build());

        return new Auth(res.getIsValid(), res.getAuthoritiesList());
    }
}
