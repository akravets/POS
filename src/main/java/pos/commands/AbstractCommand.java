package pos.commands;

import lombok.extern.slf4j.Slf4j;
import pos.helpers.POSHelper;
import pos.services.POSService;

@Slf4j
public abstract class AbstractCommand implements Command {
    POSService service;
    POSHelper posHelper;

    public AbstractCommand(POSService service, POSHelper posHelper){
        this.service = service;
        this.posHelper = posHelper;
    }
}
