package bhtu.work.tthsstatisticservice.services;

import bhtu.work.tthsstatisticservice.models.AwardLevel;
import bhtu.work.tthsstatisticservice.models.PrizeValue;
import bhtu.work.tthsstatisticservice.proto.*;
import com.google.common.util.concurrent.Futures;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.Future;

@Service
public class RewardDetailGrpcClient {
    private static bhtu.work.tthsstatisticservice.models.AwardPeriod mapAP(AwardPeriod pAwardPeriod) {
        var mAwardPeriod = new bhtu.work.tthsstatisticservice.models.AwardPeriod();
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

    private static bhtu.work.tthsstatisticservice.models.PrizePeriod mapPP(PrizePeriod pPrizePeriod) {
        var mPrizePeriod = new bhtu.work.tthsstatisticservice.models.PrizePeriod();
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
    AwardServiceGrpc.AwardServiceFutureStub awardClient;
    @GrpcClient("grpc-prize-period-service")
    PrizeServiceGrpc.PrizeServiceFutureStub prizeClient;

    public Future<bhtu.work.tthsstatisticservice.models.AwardPeriod> getAwardPeriod(LocalDate dateOfApply) {
        var req = Date.newBuilder().setDateOfApply(dateOfApply.toString()).build();

        return Futures.lazyTransform(awardClient.getByDate(req), RewardDetailGrpcClient::mapAP);
    }

    public Future<bhtu.work.tthsstatisticservice.models.PrizePeriod> getPrizePeriod(LocalDate dateOfApply) {
        var req = Date.newBuilder().setDateOfApply(dateOfApply.toString()).build();

        return Futures.lazyTransform(prizeClient.getByDate(req), RewardDetailGrpcClient::mapPP);
    }
}
