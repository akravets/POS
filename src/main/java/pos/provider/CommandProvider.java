package pos.provider;

import org.apache.commons.lang3.StringUtils;
import pos.commands.AbstractCommand;
import pos.commands.Command;

import java.util.Map;
import java.util.Optional;

/**
 * Provides access to available {@link Command}s
 */
public class CommandProvider {
    Map<String, AbstractCommand> commandMap;

    /**
     * Constructor
     * @param commandMap {@link Map} of {@link Command} and its code
     */
    public CommandProvider(Map<String, AbstractCommand> commandMap){
        this.commandMap = commandMap;
    }

    /**
     * Map of commands
     * @return Returns {@link Map} of {@link Command} and its code
     */
    public Map<String, AbstractCommand> getcommands() {
        return commandMap;
    }

    /**
     * Given commandCode returns {@link Command} associated with this code
     * @param commandCode Command code for which {@link Command} to be found
     * @return Returns {@link Command} for <code>commandCode</code>
     */
    public Optional<AbstractCommand> getCommandByCommandCode(String commandCode){
        if(StringUtils.isNumeric(commandCode)){
            return Optional.of(commandMap.get("*"));
        }
        return Optional.ofNullable(commandMap.get(commandCode));
    }
}
