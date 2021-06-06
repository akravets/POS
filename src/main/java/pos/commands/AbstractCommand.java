package pos.commands;

import pos.POSHelper;
import pos.services.POSService;

public abstract class AbstractCommand implements Command {
    POSService service;
    POSHelper posHelper;

    public AbstractCommand(POSService service, POSHelper posHelper){
        this.service = service;
        this.posHelper = posHelper;
    }
}
