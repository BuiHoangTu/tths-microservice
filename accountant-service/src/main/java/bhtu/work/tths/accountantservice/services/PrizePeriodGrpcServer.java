package bhtu.work.tths.accountantservice.services;

import org.springframework.beans.factory.annotation.Autowired;

import bhtu.work.tths.accountantservice.proto.Date;
import bhtu.work.tths.accountantservice.proto.PrizePeriod;
import bhtu.work.tths.accountantservice.proto.PrizeServiceGrpc.PrizeServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class PrizePeriodGrpcServer extends PrizeServiceImplBase{
    private PrizePeriod mapPP(bhtu.work.tths.accountantservice.models.PrizePeriod mPrizePeriod) {
        var pPPBuilder = PrizePeriod.newBuilder();

        pPPBuilder.setDateOfApply(mPrizePeriod.getDateOfApply().toString());

        for (var rt : mPrizePeriod.getRewardTypes()) {
            var pPVBuilder = PrizePeriod.PrizeValue.newBuilder();

            pPVBuilder.setNameOfPrize(rt.getNameOfPrize())
                    .setUnitPrize(rt.getUnitPrice());

            pPPBuilder.addPrizeValues(pPVBuilder);
        }

        return pPPBuilder.build();
    }

    private final PrizePeriodService service;

    @Autowired
    public PrizePeriodGrpcServer(PrizePeriodService service) {
        this.service = service;
    }

    @Override
    public void getByDate(Date request, StreamObserver<PrizePeriod> responseObserver) {
        var prizePeriod = service.getAwardPeriod(request.getDateOfApply());

        var pPrizePeriod = mapPP(prizePeriod);

        responseObserver.onNext(pPrizePeriod);
        responseObserver.onCompleted();
    }

}
