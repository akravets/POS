package pos.commands;

import lombok.extern.slf4j.Slf4j;
import pos.helpers.POSHelper;
import pos.exception.CommandException;
import pos.models.Item;
import pos.models.Purchase;
import pos.models.TaxRate;
import pos.provider.CommandProvider;
import pos.services.POSService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Slf4j
/**
 * Performs total command for transaction
 */
public class TotalCommand extends AbstractCommand {
    private final Purchase purchase;

    public TotalCommand(POSService service, POSHelper posHelper, Purchase purchase) {
        super(service, posHelper);
        this.purchase = purchase;
    }

    @Override
    public void execute(String input) throws CommandException {
        List<Item> items = purchase.getItems();

        if(items.size() == 0){
            System.out.println("There are no items to checkout");
            log.debug("no items to checkout: Purchase.getItems() is empty");
            return;
        }

        Map<TaxRate, Double> groupedTaxRateMap = service.groupTotalTaxByJurisdiction(items);

        final double subTotal = items.stream().map(item -> item.getPrice()).reduce(0.00, Double::sum);
        final double totalTax = service.getTaxForAllItems(items);
        final double total = Double.valueOf(subTotal) + Double.valueOf(totalTax);

        System.out.println("Kwik-E-Mart");
        System.out.println("==================================================");
        items.stream().forEach(item -> {
            System.out.print(pad(20, item.getName()));
            System.out.print(pad(15, item.getSku()));
            System.out.print(pad(5, format(item.getPrice())));
            System.out.print("(" + item.getTaxCategory() + ")");
            System.out.println();
        });

        System.out.println("==================================================");
        System.out.println(pad(20, "Subtotal") + format(subTotal));

        groupedTaxRateMap.forEach((k,v) -> {
            System.out.print(pad(20, k.getName()));
            System.out.println(pad(20, format(v)));
        });

        System.out.println(pad(20, "Total") + format(total));

        System.out.println("==================================================");

        System.out.println("Please enter payment amount");
        Scanner scanner = new Scanner(System.in);
        double paymentAmount = Double.valueOf(scanner.nextLine());

        while (paymentAmount < total){
            System.out.println("Payment amount must be greater than total");
            paymentAmount = Double.valueOf(scanner.nextLine());
        }

        System.out.println(pad(20, "Payment Paid") + format(paymentAmount));
        System.out.println(pad(20, "Change Due") + format(paymentAmount - total));

        purchase.getItems().clear();
    }

    private String format(double value){
        return posHelper.formatPrice(value);
    }

    public String pad(int padding, String value){
        return posHelper.pad(padding, value);
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
