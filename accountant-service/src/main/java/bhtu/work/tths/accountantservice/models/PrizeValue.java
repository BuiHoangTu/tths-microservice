package bhtu.work.tths.accountantservice.models;

import lombok.Data;

/**
 * price of a type of prize
 */
@Data
public class PrizeValue {
    /**
     * Phan thuong: keo(cai), banh(goi)
     */
    private String nameOfPrize;
    private int unitPrice;
}