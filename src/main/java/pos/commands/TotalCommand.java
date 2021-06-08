package pos.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import pos.POSHelper;
import pos.exception.CommandException;
import pos.functions.ApplyTaxFunction;
import pos.models.Item;
import pos.models.Purchase;
import pos.models.TaxRate;
import pos.services.POSService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class TotalCommand extends AbstractCommand {
    private final Purchase purchase;

    public TotalCommand(POSService service, POSHelper posHelper, Purchase purchase) {
        super(service, posHelper);
        this.purchase = purchase;
    }

    @Override
    public void execute(String input) throws CommandException {
        ApplyTaxFunction function = new ApplyTaxFunction();
        Map<TaxRate, Double> taxMap = new HashMap<>();

        purchase.getItems().forEach(x -> {
            taxMap.put(TaxRate.CITY, function.apply(TaxRate.CITY, x));
            taxMap.put(TaxRate.STATE, function.apply(TaxRate.STATE, x));
            taxMap.put(TaxRate.COUNTY, function.apply(TaxRate.COUNTY, x));
        });

        final Double reduce = taxMap.values().stream().reduce(0.0, Double::sum);
        System.out.println();
    }

    @Override
    public String getName() {
        return "Total";
    }

    @Override
    public String getDescription() {
        return "Prints the receipt";
    }

    @Override
    public String getCommandCode() {
        return "t";
    }
}
