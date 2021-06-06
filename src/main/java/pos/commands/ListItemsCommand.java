package pos.commands;

import org.springframework.beans.factory.annotation.Autowired;
import pos.POSHelper;
import pos.services.POSService;

public class ListItemsCommand implements Command {
    @Autowired
    POSService service;

    @Autowired
    POSHelper posHelper;

    @Override
    public void execute() {
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
