package pos.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pos.POSHelper;
import pos.commands.Command;
import pos.commands.CommandProvider;
import pos.commands.ExitCommand;
import pos.commands.ListItemsCommand;

import java.util.LinkedHashSet;
import java.util.Set;

@Configuration
public class POSConfiguration {
    @Bean
    CommandProvider getCommandProvider() {
        Set<Command> commandSet = new LinkedHashSet<>();
        commandSet.add(new ExitCommand());
        commandSet.add(new ListItemsCommand());

        CommandProvider provider = new CommandProvider(commandSet);

        return provider;
    }

    @Bean
    POSHelper getPOSHelper() {
        return new POSHelper();
    }
}
