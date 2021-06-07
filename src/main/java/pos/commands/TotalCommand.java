package pos.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import pos.POSHelper;
import pos.exception.CommandException;
import pos.models.Item;
import pos.models.Purchase;
import pos.services.POSService;

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
        log.debug("Executing total command");
        Set<Item> items = purchase.getItems();
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
