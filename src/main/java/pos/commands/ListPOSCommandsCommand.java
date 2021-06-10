package pos.commands;

import org.springframework.stereotype.Component;
import pos.helpers.POSHelper;
import pos.services.POSService;

@Component
public class ListPOSCommandsCommand extends AbstractCommand {
    public ListPOSCommandsCommand(POSService service, POSHelper posHelper){
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
