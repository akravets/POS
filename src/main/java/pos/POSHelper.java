package pos;

import org.springframework.beans.factory.annotation.Autowired;
import pos.commands.AbstractCommand;
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
        Set<AbstractCommand> commandSet = service.getCommands();

        System.out.println("POS Commands");
        System.out.println("============\n");

        commandSet.forEach(c -> {
            StringBuffer buffer = new StringBuffer();
            buffer.append(c.getName());
            buffer.append(" (");
            buffer.append(c.getCommandCode());
            buffer.append(")");

            String partOne = String.format("%-" + 20 + "s", buffer.toString());

            System.out.println(partOne + c.getDescription());
        });
    }
}
