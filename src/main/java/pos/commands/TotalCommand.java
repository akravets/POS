package pos.commands;

import lombok.extern.slf4j.Slf4j;
import pos.helpers.POSHelper;
import pos.exception.CommandException;
import pos.functions.ApplyTaxFunction;
import pos.models.Item;
import pos.models.Purchase;
import pos.models.TaxRate;
import pos.services.POSService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

        List<Item> items = purchase.getItems();

        items.forEach(x -> {
            taxMap.put(TaxRate.CITY, function.apply(TaxRate.CITY, x));
            taxMap.put(TaxRate.STATE, function.apply(TaxRate.STATE, x));
            taxMap.put(TaxRate.COUNTY, function.apply(TaxRate.COUNTY, x));
        });

        final double subTotal = items.stream().map(item -> item.getPrice()).reduce(0.0, Double::sum);
        final double tax = posHelper.formatPrice(taxMap.values().stream().reduce(0.0, Double::sum));
        final double total = posHelper.formatPrice(subTotal + tax);

        System.out.println("Subtotal: " + subTotal);
        System.out.println("Tax: " + tax);
        System.out.println("Total: " + total);

        System.out.println("Please enter payment");
        Scanner scanner = new Scanner(System.in);

        double payment = posHelper.formatPrice(Double.valueOf(scanner.nextLine()));

        while (payment < total){
            System.out.println("Payment amount must be greater than total");
            payment = posHelper.formatPrice(Double.valueOf(scanner.nextLine()));
        }

        System.out.println("Kwik-E-Mart");
        System.out.println("==================================================");
        purchase.getItems().stream().forEach(item -> {
            System.out.print(String.format("%-" + 20 + "s", item.getName()));
            System.out.print(String.format("%-" + 15 + "s", item.getSku()));
            System.out.print(String.format("%-" + 5 + "s", item.getPrice()));
            System.out.print(String.format("%-" + 2 + "s", item.getTaxCategory().toString()));
            System.out.println();
        });

        System.out.println("----------------------------------------------------");
        System.out.println("Subtotal: " + subTotal);
        System.out.println("Tax: " + tax);
        System.out.println("Total: " + total);

        taxMap.forEach((k,v) -> {
            System.out.print(k.getName());
            System.out.print(v);
        });
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
