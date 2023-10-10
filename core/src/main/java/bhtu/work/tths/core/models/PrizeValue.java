package bhtu.work.tths.core.models;

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