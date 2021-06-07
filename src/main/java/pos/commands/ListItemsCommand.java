package pos.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import pos.POSHelper;
import pos.services.POSService;

@Component
public class ListItemsCommand extends AbstractCommand {
    public ListItemsCommand(POSService service, POSHelper posHelper){
        super(service, posHelper);
    }

    @Override
    public void execute(String input) {
        posHelper.printAllCommands();
    }

    @Override
    public String getName() {
        return "List";
    }

    @Override
    public String getDescription() {
        return "List items";
    }

    @Override
    public String getCommandCode() {
        return "l";
    }
}
