package pos.commands;

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
}
