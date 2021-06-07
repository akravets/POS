package pos.commands;

import pos.POSHelper;
import pos.services.POSService;

public class ExitCommand extends AbstractCommand{
    public ExitCommand(POSService service, POSHelper posHelper) {
        super(service, posHelper);
    }

    @Override
    public void execute() {
        return;
    }

    @Override
    public String getName() {
        return "Exit";
    }

    @Override
    public String getDescription() {
        return "Exits application";
    }

    @Override
    public String getCommandCode() {
        return "exit";
    }
}
