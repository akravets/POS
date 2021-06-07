package pos.commands;

import lombok.extern.slf4j.Slf4j;
import pos.POSHelper;
import pos.exception.CommandException;
import pos.exception.SKUNotFoundException;
import pos.models.Item;
import pos.services.POSService;

import java.util.Set;

@Slf4j
public class SKULookupCommand extends AbstractCommand {
    private final String input;

    public SKULookupCommand(POSService service, POSHelper posHelper, String input) {
        super(service, posHelper);
        this.input = input;
    }

    @Override
    public void execute() throws SKUNotFoundException {
        Set<Item> itemBySKU = service.findItemBySKU(input);
        if(itemBySKU.size() == 0){
            throw new SKUNotFoundException("404");
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
        return "enter input";
    }
}
