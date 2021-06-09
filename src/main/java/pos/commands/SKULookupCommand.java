package pos.commands;

import lombok.extern.slf4j.Slf4j;
import pos.helpers.POSHelper;
import pos.exception.SKUNotFoundException;
import pos.models.Item;
import pos.models.Purchase;
import pos.services.POSService;

import java.util.List;
import java.util.Optional;
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
        if(input.length() < 3){
            throw new IllegalArgumentException("SKU length must be at least 3 characters");
        }
        Optional<List<Item>> items = service.findItemBySKU(input);

        items.orElseThrow(() -> new SKUNotFoundException(input));

        final List<Item> itemList = items.get();

        if(itemList.size() == 0){
            throw new SKUNotFoundException(input);
        }
        else if(itemList.size() > 1){
            System.out.println("Mulitple SKUs found, pick one:\n");
            itemList.forEach(i -> {
                System.out.print(posHelper.pad(20, i.getSku()));
                System.out.println(i.getName());
            });
        }
        else {
            System.out.println("\nItem added:");

            final Item item = itemList.get(0);
            System.out.print(posHelper.pad(20, item.getSku()));
            System.out.println(item.getPrice());

            purchase.getItems().add(item);
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
