package bhtu.work.tths.accountantservice.services;

import bhtu.work.tths.accountantservice.models.AwardPeriod;
import org.springframework.beans.factory.annotation.Autowired;

import bhtu.work.tthsaccountantservice.proto.AwardPeriod;
import bhtu.work.tthsaccountantservice.proto.AwardServiceGrpc.AwardServiceImplBase;
import bhtu.work.tthsaccountantservice.proto.Date;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class AwardPeriodGrpcServer extends AwardServiceImplBase {
    private AwardPeriod mapAP(AwardPeriod mAwardPeriod) {
        var pAPBuilder = AwardPeriod.newBuilder();

        pAPBuilder.setDateOfApply(mAwardPeriod.getDateOfApply().toString());

        for (var al : mAwardPeriod.getAwardLevels()) {
            var pALBuilder = AwardPeriod.AwardLevel.newBuilder();

            pALBuilder.setAchievement(al.getAchievement())
                    .setPrizeValue(al.getPrizeValue());

            pAPBuilder.addAwardLevels(pALBuilder);
        }

        return pAPBuilder.build();
    }

    private final AwardPeriodService service;

    @Autowired
    public AwardPeriodGrpcServer(AwardPeriodService service) {
        this.service = service;
    }

    @Override
    public void getByDate(Date request, StreamObserver<AwardPeriod> responseObserver) {
        var awardPeriod = service.getAwardPeriod(request.getDateOfApply());

        var pAwardPeriod = mapAP(awardPeriod);

        responseObserver.onNext(pAwardPeriod);
        responseObserver.onCompleted();
    }

}
