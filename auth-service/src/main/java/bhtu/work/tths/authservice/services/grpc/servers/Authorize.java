package bhtu.work.tths.authservice.services.grpc.servers;

import bhtu.work.tths.authservice.proto.AuthorizeGrpc;
import bhtu.work.tths.authservice.proto.Jwt;
import bhtu.work.tths.authservice.proto.Verifications;
import bhtu.work.tths.authservice.security.jwt.IJwtService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class Authorize extends AuthorizeGrpc.AuthorizeImplBase {
    private final IJwtService jwtUtils;

    @Autowired
    public Authorize(IJwtService jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void checkJwt(Jwt jwt, StreamObserver<Verifications> responseObserver) {
        var builder = Verifications.newBuilder();

        try {
             builder.addAllAuthorities(jwtUtils.extractAuthorities(jwt.getJwt()));
             builder.setIsValid(true);
        } catch (Exception e) {
            builder.setIsValid(false);
        } finally {
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        }
    }
}
