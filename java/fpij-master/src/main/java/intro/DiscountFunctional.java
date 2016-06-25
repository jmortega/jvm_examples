package intro;

import java.math.BigDecimal;

/**
 * User: starblood
 * Date: 2014. 4. 9.
 * Time: 오후 2:52
 */
public class DiscountFunctional {
    public static void main(final String[] args) {
        final BigDecimal totalOfDiscountedPrices =
                Prices.prices.stream()
                        .filter(price -> price.compareTo(BigDecimal.valueOf(20)) > 0)
                        .map(price -> price.multiply(BigDecimal.valueOf(0.9)))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("Total of discounted prices: " + totalOfDiscountedPrices);
    }
}