package pos;

import org.springframework.beans.factory.annotation.Autowired;
import pos.commands.Command;
import pos.services.POSService;

import java.util.Set;

/**
 * Contains a set of helper methods that otherwise don't fit into specific category
 */
public class POSHelper {
    @Autowired
    private POSService service;

    public POSHelper() {

    }

    public void printAllCommands(){
        Set<Command> commandSet = service.getCommands();

        System.out.println("POS Commands");
        System.out.println("============");

        commandSet.forEach(commandEnum -> {
            System.out.println(commandEnum.getName() + "\t" + commandEnum.getDescription() + "\t" + commandEnum.getCommandCode());
        });
    }
}
