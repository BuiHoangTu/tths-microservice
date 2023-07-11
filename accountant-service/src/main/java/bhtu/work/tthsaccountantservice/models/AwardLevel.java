package bhtu.work.tthsaccountantservice.models;

/**
 * Amount of money for each achivement 
 */
public class AwardLevel {
    private String achievement;
    private int prizeValue;

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public int getPrizeValue() {
        return prizeValue;
    }

    public void setPrizeValue(int prizeValue) {
        this.prizeValue = prizeValue;
    }

}