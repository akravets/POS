package pos.commands;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

/**
 * Provides access to available {@link Command}s
 */
public class CommandProvider {
    Set<Command> commandSet;

    /**
     * Constructor
     * @param commandSet {@link Set} of {@link Command}s
     */
    public CommandProvider(Set<Command> commandSet){
        this.commandSet = commandSet;

    }

    /**
     * Set of commands
     * @return Returns {@link Set} of {@link Command}s
     */
    public Set<Command> getcommands() {
        return commandSet;
    }

    /**
     * Given commandCode returns {@link Command} associated with this code
     * @param commandCode Command code for which {@link Command} to be found
     * @return Returns {@link Command} for <code>commandCode</code>
     */
    public Optional<Command> getCommandByCommandCode(String commandCode){
        return commandSet.stream().filter(c -> c.getCommandCode().equals(commandCode)).findFirst();
    }
}
