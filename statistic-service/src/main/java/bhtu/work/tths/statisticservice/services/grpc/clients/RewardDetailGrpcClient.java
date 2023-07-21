package bhtu.work.tths.statisticservice.services.grpc.clients;

import bhtu.work.tths.statisticservice.models.AwardLevel;
import bhtu.work.tths.statisticservice.models.AwardPeriod;
import bhtu.work.tths.statisticservice.models.PrizePeriod;
import bhtu.work.tths.statisticservice.models.PrizeValue;
import com.google.common.util.concurrent.Futures;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.Future;

@Service
public class RewardDetailGrpcClient {
    private static AwardPeriod mapAP(bhtu.work.tths.statisticservice.proto.AwardPeriod pAwardPeriod) {
        var mAwardPeriod = new AwardPeriod();
        mAwardPeriod.setDateOfApply(LocalDate.parse(pAwardPeriod.getDateOfApply()));
        mAwardPeriod.setAwardLevels(pAwardPeriod.getAwardLevelsList()
                .stream()
                .map((pAL) -> {
                    var mAL = new AwardLevel();
                    mAL.setAchievement(pAL.getAchievement());
                    mAL.setPrizeValue(pAL.getPrizeValue());
                    return mAL;
                })
                .toList());
        return mAwardPeriod;
    }

    private static PrizePeriod mapPP(bhtu.work.tths.statisticservice.proto.PrizePeriod pPrizePeriod) {
        var mPrizePeriod = new PrizePeriod();
        mPrizePeriod.setDateOfApply(LocalDate.parse(pPrizePeriod.getDateOfApply()));
        mPrizePeriod.setRewardTypes(pPrizePeriod.getPrizeValuesList()
                .stream()
                .map((pAV) -> {
                    var mAL = new PrizeValue();
                    mAL.setNameOfPrize(pAV.getNameOfPrize());
                    mAL.setUnitPrice(pAV.getUnitPrize());
                    return mAL;
                })
                .toList());
        return mPrizePeriod;
    }

    @GrpcClient("grpc-reward-detail-service")
    bhtu.work.tths.statisticservice.proto.AwardServiceGrpc.AwardServiceFutureStub awardClient;
    @GrpcClient("grpc-prize-period-service")
    bhtu.work.tths.statisticservice.proto.PrizeServiceGrpc.PrizeServiceFutureStub prizeClient;

    public Future<AwardPeriod> getAwardPeriod(LocalDate dateOfApply) {
        var req = bhtu.work.tths.statisticservice.proto.Date.newBuilder().setDateOfApply(dateOfApply.toString()).build();

        return Futures.lazyTransform(awardClient.getByDate(req), RewardDetailGrpcClient::mapAP);
    }

    public Future<PrizePeriod> getPrizePeriod(LocalDate dateOfApply) {
        var req = bhtu.work.tths.statisticservice.proto.Date.newBuilder().setDateOfApply(dateOfApply.toString()).build();

        return Futures.lazyTransform(prizeClient.getByDate(req), RewardDetailGrpcClient::mapPP);
    }
}
