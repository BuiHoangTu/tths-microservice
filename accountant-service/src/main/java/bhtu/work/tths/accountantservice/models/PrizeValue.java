package bhtu.work.tths.accountantservice.models;

/**
 * price of a type of prize
 */
public class PrizeValue {
    /**
     * Phan thuong: keo(cai), banh(goi)
     */
    private String nameOfPrize;
    private int unitPrice;

    public String getNameOfPrize() {
        return nameOfPrize;
    }

    public void setNameOfPrize(String nameOfPrize) {
        this.nameOfPrize = nameOfPrize;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

}