package pos.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pos.helpers.POSHelper;
import pos.commands.*;
import pos.models.Purchase;
import pos.provider.CommandProvider;
import pos.provider.DataProvider;
import pos.services.POSService;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Configuration
public class POSConfiguration {
    @Autowired
    POSService posService;

    @Autowired
    Purchase purchase;

    @Bean
    public CommandProvider getCommandProvider() {
        HashMap<String, AbstractCommand> commandMap = new LinkedHashMap<>();

        AbstractCommand exitCommand = new ExitCommand(posService, getPOSHelper());
        AbstractCommand listCommand = new ListPOSCommandsCommand(posService, getPOSHelper());
        AbstractCommand skuLookupCommand = new SKULookupCommand(posService, getPOSHelper(), purchase);
        AbstractCommand totalCommand = new TotalCommand(posService, getPOSHelper(), purchase);

        commandMap.put(exitCommand.getCommandCode(), exitCommand);
        commandMap.put(listCommand.getCommandCode(), listCommand);
        commandMap.put(skuLookupCommand.getCommandCode(), skuLookupCommand);
        commandMap.put(totalCommand.getCommandCode(), totalCommand);

        return new CommandProvider(commandMap);
    }

    @Bean
    public POSHelper getPOSHelper() {
        return new POSHelper();
    }

    @Bean
    public DataProvider getDataProvider() {
        return new DataProvider();
    }
}
