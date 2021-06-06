package pos.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pos.POSHelper;
import pos.commands.*;
import pos.services.POSService;

import java.util.LinkedHashSet;
import java.util.Set;

@Configuration
public class POSConfiguration {
    @Autowired
    POSService posService;

    @Bean
    public CommandProvider getCommandProvider() {
        Set<AbstractCommand> commandSet = new LinkedHashSet<>();
        commandSet.add(new ExitCommand(posService, getPOSHelper()));
        commandSet.add(new ListItemsCommand(posService, getPOSHelper()));

        CommandProvider provider = new CommandProvider(commandSet);

        return provider;
    }

    @Bean
    public POSHelper getPOSHelper() {
        return new POSHelper();
    }
}
