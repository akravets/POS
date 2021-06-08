package pos.functions;

import pos.models.Item;
import pos.models.TaxCategory;
import pos.models.TaxRate;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.function.BiFunction;

/**
 * Calculates tax for an {@link Item} based on {@link TaxRate}
 */
public class ApplyTaxFunction implements BiFunction<TaxRate, Item, Double> {
    private DecimalFormat df;

    public ApplyTaxFunction(){
        df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
    }

    @Override
    public Double apply(TaxRate taxRate, Item item) {
        // https://github.com/noInterestIfPaidInFull/POS/issues/17
        if(item.getTaxCategory() == TaxCategory.g && (taxRate == TaxRate.STATE || taxRate == TaxRate.COUNTY)){
            return item.getPrice();
        }

        final double value = ((taxRate.getTaxRate() / 100) * item.getPrice());

        return Double.valueOf(df.format(value));
    }
}
