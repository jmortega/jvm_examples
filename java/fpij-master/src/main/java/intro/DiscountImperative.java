package intro;

import java.math.BigDecimal;

/**
 * User: starblood
 * Date: 2014. 4. 9.
 * Time: 오후 2:26
 */
public class DiscountImperative {
    public static void main(final String[] args) {
        BigDecimal totalOfDiscountedPrices = BigDecimal.ZERO;

        for(BigDecimal price : Prices.prices) {
            if(price.compareTo(BigDecimal.valueOf(20)) > 0)
                totalOfDiscountedPrices =
                        totalOfDiscountedPrices.add(price.multiply(BigDecimal.valueOf(0.9)));
        }
        System.out.println("Total of discounted prices: " + totalOfDiscountedPrices);
    }
}