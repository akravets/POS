package pos.commands;

import lombok.extern.slf4j.Slf4j;
import pos.helpers.POSHelper;
import pos.exception.SKUNotFoundException;
import pos.models.Item;
import pos.models.Purchase;
import pos.services.POSService;

import java.util.List;
import java.util.Set;

@Slf4j
public class SKULookupCommand extends AbstractCommand {
    private final Purchase purchase;
    private String input;

    public SKULookupCommand(POSService service, POSHelper posHelper, Purchase purchase) {
        super(service, posHelper);
        this.purchase = purchase;
    }

    @Override
    public void execute(String input) throws SKUNotFoundException {
        List<Item> items = service.findItemBySKU(input);

        if(items.size() == 0){
            throw new SKUNotFoundException(input);
        }
        else if(items.size() > 1){
            System.out.println("Mulitple SKUs found, pick one:\n");
            items.forEach(i -> {
                System.out.print(String.format("%-" + 20 + "s", i.getSku()));
                System.out.println(i.getName());
            });
        }
        else {
            System.out.println("\nItem added:");
            items.forEach(i -> {
                System.out.print(String.format("%-" + 20 + "s", i.getName()));
                System.out.println(i.getPrice());
            });

            purchase.getItems().add(items.iterator().next());
            System.out.println("\r\n");
            
        }
    }

    @Override
    public String getName() {
        return "SKU Lookup";
    }

    @Override
    public String getDescription() {
        return "Search for SKU based on partial or full input, by entering numbers";
    }

    @Override
    public String getCommandCode() {
        return "*";
    }
}
